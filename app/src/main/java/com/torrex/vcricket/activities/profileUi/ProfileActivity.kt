package com.torrex.vcricket.activities.profileUi

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.torrex.vcricket.R
import com.torrex.vcricket.activities.mainUi.LoginActivity
import com.torrex.vcricket.activities.mainUi.MainActivity
import com.torrex.vcricket.constants.DataBaseConstant
import com.torrex.vcricket.constants.GlobalConstant
import com.torrex.vcricket.constants.GlobalFunctions
import com.torrex.vcricket.databinding.ActivityProfileBinding
import com.torrex.vcricket.firebase.FireBaseUserDataBase
import com.torrex.vcricket.models.User
import com.torrex.vcricket.modules.BaseActivity
import com.torrex.vcricket.roomDatabase.RoomDatabaseBuilder
import com.torrex.vcricket.roomDatabase.VCricketDatabase
import com.torrex.vcricket.roomDatabase.databaseHelper.VUserDatabaseHelper
import com.torrex.vcricket.roomDatabase.roomModels.VUser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileActivity : BaseActivity(),
    AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var db: VCricketDatabase
    private lateinit var profileViewModel:ProfileViewModel
    private lateinit var mUser:User
    private var mSelectedProfileImage: Uri?=null
    private var mProfileImage:String=""

    val genderSpinner= arrayOf("Select","Male","Female")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_profile)


        if (intent.hasExtra(GlobalConstant.USER_MODEL_DATA)){
            mUser=intent.getParcelableExtra(GlobalConstant.USER_MODEL_DATA)!!
            mProfileImage=mUser.image
        }
        profileViewModel= ViewModelProvider(this,ProfileViewModelFactory(this,mUser))[ProfileViewModel::class.java]


        //Setting Binding properties-------------
        binding.profileViewModel=profileViewModel
        binding.lifecycleOwner=this

        //Spinner functionality
        val genderSpinnerAdaptor=ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,genderSpinner)
        genderSpinnerAdaptor.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        binding.spinnerProfileGender.adapter=genderSpinnerAdaptor
        binding.spinnerProfileGender.onItemSelectedListener=this

    }



    //Functions for the click Event-------------------------------------
    fun onEditClick(view: View?){
        if (view==binding.btnProfileEdit){
            if (binding.btnProfileEdit.text.toString().trim { it<= ' ' }=="Edit"){

                binding.etProfileEmail.isEnabled = TextUtils.isEmpty(binding.etProfileEmail.text.toString().trim { it<= ' ' })
                binding.etProfileFirstName.isEnabled=true
                binding.etProfileLastName.isEnabled=true
                binding.etProfileMobile.isEnabled=false
                //binding.spinnerProfileGender.visibility=View.VISIBLE
                binding.llSpinner.visibility=View.VISIBLE

                binding.btnProfileEdit.setText("Save")
            }
            else{
                if (validateDetails()){
                    showProgressDialog("Saving!!!")
                    //First Upload Image than details to user Account
                    if (mSelectedProfileImage!=null){
                        FireBaseUserDataBase().uploadUserImageToCloudStorage(this,mSelectedProfileImage!!,"user")
                    }
                    else{
                        profileViewModel.updateUserDetails()
                        FireBaseUserDataBase().updateUserProfile(this,profileViewModel.mUpdatedUser,mUser.id)
                    }
                }
                //TODO("Submit functionality")
                Log.v("Button ","Save clicked")
                Toast.makeText(this,"Save Clicked",Toast.LENGTH_SHORT).show()
            }
        }
    }

    //OnLogOut Clicked

    fun onLoOut(view: View?){
        if (view==binding.llProfileLogout){
            val dialog = AlertDialog.Builder(this).setTitle("LogOut")
                .setMessage(R.string.message_log_out)
            dialog.setPositiveButton("Yes"){dialogInterface,which->
                showProgressDialog("Logging Out")
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            dialog.setNegativeButton("No"){dialogInterface,which->
                Log.v("Dialog","Dialog Dismissed")
            }
            dialog.setCancelable(false)
            dialog.show()
        }
    }

    //ONImage Edit Button Clicked
    fun onEditImage(view: View?){
        if (view==binding.ivUserProfilePhotoEdit){
            if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                GlobalFunctions.showImageChooser(this)
            }
            else{
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),GlobalConstant.READ_EXTERNAL_STORAGE_CODE)
            }
            Toast.makeText(this,"Image Clicked",Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateDetails(): Boolean {
        return when {
            TextUtils.isEmpty(binding.etProfileEmail.text.toString().trim { it<= ' ' })->{
                showErrorSnackBar("Please enter Email",true)
                false
            }
            TextUtils.isEmpty(binding.etProfileFirstName.text.toString().trim { it<= ' ' })->{
                showErrorSnackBar("Please enter FirstName",true)
                false
            }
            TextUtils.isEmpty(binding.etProfileLastName.text.toString().trim { it<= ' ' })->{
                showErrorSnackBar("Please enter LastName",true)
                false
            }

            TextUtils.isEmpty(binding.tvUserProfileGender.text.toString().trim { it<= ' ' })->{
                showErrorSnackBar("Please enter Gender",true)
                false
            }
            (mProfileImage==null || mProfileImage.isEmpty() && mSelectedProfileImage==null)->{
                showErrorSnackBar("Please Select User Pic",true)
                false
            }
            //TODO("Enter function for Image also")
            else->{
                true
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position==0){
            binding.tvUserProfileGender.text=mUser.gender
        }
        else{
            binding.tvUserProfileGender.text=genderSpinner[position]
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //tv_user_profile_gender.text="select"
    }

    fun userUpdatedSuccessfully(){
        FireBaseUserDataBase().getUserFireStore(this,mUser.id)

    }

    //Image uploaded Successfully
    fun imageUploadedSuccessfully(url:String)
    {
        mProfileImage=url
        profileViewModel.userProfileImage.value=url
        profileViewModel.updateUserDetails()
        FireBaseUserDataBase().updateUserProfile(this,profileViewModel.mUpdatedUser,mUser.id)

    }



    //Save User To RoomDatabase
    private fun saveUserToDatabase(user: VUser) {
        db= RoomDatabaseBuilder.getInstance(this)
        GlobalScope.launch(){
            VUserDatabaseHelper(db).saveUser(user)
            //TODO("change user to vUser Type for roomdatabase")
        }
    }

    //Getting user from Profile activity and passing to MainActivity to save user in database while creating and updating
    fun getUserSuccess(user:User){
        hideProgressDialog()
        //check for User Updated or added------------------
        if (user!=null){
            val vUser= VUser(0,user.id,user.firstName,user.lastName,user.email,
                user.mobile,user.gender,user.dob,user.image)
            saveUserToDatabase(vUser)
            Log.v("USER_SAVED_IN_DATABASE","User saved")
        }
        Toast.makeText(this,"Details Saved Successfully",Toast.LENGTH_SHORT).show()
        finish()
        val intent=Intent(this,MainActivity::class.java)
        intent.putExtra(GlobalConstant.USER_ADDED_UPDATED,true)
        startActivity(intent)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode==Activity.RESULT_OK){
            if (requestCode==GlobalConstant.PICK_IMAGE_REQUEST_CODE){
                if (data!=null){
                    mSelectedProfileImage=data.data
                    GlobalFunctions.loadUserPicture(this,mSelectedProfileImage!!,binding.ivUserProfilePhoto)
                }
            }
        }
    }
}