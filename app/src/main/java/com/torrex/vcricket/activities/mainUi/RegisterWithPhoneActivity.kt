package com.torrex.vcricket.activities.mainUi

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.torrex.vcricket.R
import com.torrex.vcricket.constants.GlobalConstant
import com.torrex.vcricket.constants.GlobalFunctions
import com.torrex.vcricket.databinding.ActivityRegisterWithPhoneBinding
import com.torrex.vcricket.firebase.FireBaseUserDataBase
import com.torrex.vcricket.models.User
import com.torrex.vcricket.modules.BaseActivity
import com.torrex.vcricket.roomDatabase.RoomDatabaseBuilder
import com.torrex.vcricket.roomDatabase.VCricketDatabase
import com.torrex.vcricket.roomDatabase.databaseHelper.VUserDatabaseHelper
import com.torrex.vcricket.roomDatabase.roomModels.VUser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterWithPhoneActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegisterWithPhoneBinding
    var mUserId:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegisterWithPhoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpActionBar()

        if (intent.hasExtra(GlobalConstant.USER_PHONE_DATA)){
            binding.etSignUpMobilePhone.setText(intent.getStringExtra(GlobalConstant.USER_PHONE_DATA))
            binding.etSignUpMobilePhone.isEnabled=false
        }
        if (intent.hasExtra(GlobalConstant.USER_ID_PHONE_SIGNIN)){
            mUserId=intent.getStringExtra(GlobalConstant.USER_ID_PHONE_SIGNIN)!!
        }
        binding.btnSignUpPhone.setOnClickListener(this)
        binding.ibDatePickerPhone.setOnClickListener(this)
        binding.ivProfileRegisterImagePhone.setOnClickListener(this)

    }


    //setting up action Bar
    private fun setUpActionBar(){
        setSupportActionBar(binding.toolbarRegisterActivityPhone)

        val actionBar=supportActionBar
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        }
        binding.toolbarRegisterActivityPhone.setNavigationOnClickListener{onBackPressed()}
    }

    override fun onClick(v: View?) {
        if (v!=null){
            when(v.id){
                R.id.btn_signUp_phone->{
                    //Storing data in the Firebase store
                    registerUserWithPhone()
                }
                R.id.iv_profile_register_image_phone->{

                }
                R.id.ib_date_picker_phone->{
                    datePickerCustom(binding.tvSignUpDobPhone)
                }
            }
        }
    }

    private fun registerUserWithPhone() {
        if (validateUserDetails()){
            val mPhone=binding.etSignUpMobilePhone.text.toString().trim { it<=' ' }.toLong()
            var gender:String=""
            if (binding.rbMalePhone.isChecked){
                gender=GlobalConstant.MALE
            }
            else{
                gender=GlobalConstant.FEMALE
            }
            showProgressDialog("Wait")

            val user=User(mUserId,
                binding.etSignUpFirstNamePhone.text.toString().trim{it <=' '},
                binding.etSignUpLastNamePhone.text.toString().trim{it <=' '},
            "",mPhone,gender,binding.tvSignUpDobPhone.text.toString().trim { it<=' ' }
            )
            FireBaseUserDataBase().registerUser(this,user)

        }
    }


    //Validate the user details entered
    private fun validateUserDetails():Boolean{
        return when{
            TextUtils.isEmpty(binding.etSignUpFirstNamePhone.text.toString().trim { it<=' '})->{
                showErrorSnackBar("Please Enter First Name",true)
                false
            }
            TextUtils.isEmpty(binding.etSignUpLastNamePhone.text.toString().trim { it<=' '})->{
                showErrorSnackBar("Please Enter Last Name",true)
                false
            }

            TextUtils.isEmpty(binding.tvSignUpDobPhone.text.toString().trim{it<=' '})-> {
                showErrorSnackBar("Please Enter DOB",true)
                false
            }

            TextUtils.isEmpty(binding.etSignUpMobilePhone.text.toString().trim { it<=' '})->{
                showErrorSnackBar("Please Enter Correct MobileNo",true)
                false
            }
            binding.etSignUpMobilePhone.text.toString().trim { it<=' '}.length!=10->{
                showErrorSnackBar("Please Enter Correct MobileNo",true)
                false
            }
            else-> {
                true
            }
        }

    }



    fun userRegisteredSuccessWithPhone(user:User){
        hideProgressDialog()
        Log.v("Success","Success")
        Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()

        finish()
        val intent=Intent(this,MainActivity::class.java)
        //intent.putExtra(GlobalConstant.USER_MODEL_DATA,user)
        startActivity(intent)
    }



}