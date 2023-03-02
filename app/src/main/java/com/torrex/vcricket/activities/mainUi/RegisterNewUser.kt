package com.torrex.vcricket.activities.mainUi

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.torrex.vcricket.R
import com.torrex.vcricket.constants.GlobalConstant
import com.torrex.vcricket.constants.GlobalFunctions
import com.torrex.vcricket.databinding.ActivityRegisterNewUserBinding
import com.torrex.vcricket.firebase.FireBaseUserDataBase
import com.torrex.vcricket.models.User
import com.torrex.vcricket.modules.BaseActivity

class RegisterNewUser : BaseActivity(),View.OnClickListener {
    private lateinit var binding: ActivityRegisterNewUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterNewUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpActionBar()

        //Click Functionality
        binding.btnSignUp.setOnClickListener(this)
        binding.ibDatePicker.setOnClickListener(this)
        binding.ivProfileRegisterImage.setOnClickListener(this)
    }

    fun userRegisteredSuccessfully(user:User){
        hideProgressDialog()
        Log.v("RegisteredUser",user.firstName)
        Log.v("RegisteredUser","User Registered Success")
        Toast.makeText(this,"User Registered Success",Toast.LENGTH_SHORT).show()
        finish()
        startActivity(Intent(this,LoginActivity::class.java))

    }

    //setting up action Bar
    private fun setUpActionBar(){
        setSupportActionBar(binding.toolbarRegisterActivity)

        val actionBar=supportActionBar
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        }
        binding.toolbarRegisterActivity.setNavigationOnClickListener{onBackPressed()}
    }

    //Validate the user details entered
    private fun validateUserDetails():Boolean{
        return when{
            TextUtils.isEmpty(binding.etSignupEmail.text.toString().trim{it<=' '})->{
                showErrorSnackBar("Please Enter Email",true)
                false
            }

            TextUtils.isEmpty(binding.etSignUpFirstName.text.toString().trim { it<=' '})->{
                showErrorSnackBar("Please Enter First Name",true)
                false
            }
            TextUtils.isEmpty(binding.etSignUpLastName.text.toString().trim { it<=' '})->{
                showErrorSnackBar("Please Enter Last Name",true)
                false
            }

            TextUtils.isEmpty(binding.tvSignUpDob.text.toString().trim{it<=' '})-> {
                showErrorSnackBar("Please Enter DOB",true)
                false
            }

            TextUtils.isEmpty(binding.etSignUpMobile.text.toString().trim { it<=' '})->{
                showErrorSnackBar("Please Enter Correct MobileNo",true)
                false
            }
            binding.etSignUpMobile.text.toString().trim { it<=' '}.length!=10->{
                showErrorSnackBar("Please Enter Correct MobileNo",true)
                false
            }
            binding.etSignUpPassword.text.toString().trim {it<=' '}!=binding.etSignUpConfirmPassword.text.toString().trim {it<=' '}->{
                showErrorSnackBar("Please Enter correct Password",true)
                false
            }
            else-> {
                true
            }
        }

    }

    override fun onClick(v: View?) {
        if (v!=null){
            when(v.id){
                //Image Chooser
                R.id.iv_profile_register_image->{
                    //TODO("Show Image chooser ")
                    Toast.makeText(this,"Image Clicked",Toast.LENGTH_SHORT).show()

                }
                //Date picker for DOB
                R.id.ib_date_picker->{
                    datePickerCustom(binding.tvSignUpDob)
                }
                //SignUp Button
                R.id.btn_signUp-> {
                    //TODO("functionality to store image and user")
                registerUser()
                }

            }
        }
    }

    private fun registerUser(){
        if (validateUserDetails()){
            showProgressDialog("Registering User Please Wait!!!")
            val email=binding.etSignupEmail.text.toString().trim{it <=' '}
            val password = binding.etSignUpPassword.text.toString().trim { it <= ' ' }
            var userGender: String = ""
            var mobNo=binding.etSignUpMobile.text.toString().trim { it <= ' ' }.toLong()

            if (binding.rbMale.isChecked){
                userGender=GlobalConstant.MALE
            }
            else{
                userGender=GlobalConstant.FEMALE
            }

            //Creating the instance and register a new user with email and password in firebase
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener { task->
                    if (task.isSuccessful){

                        //firebase user------------------------------
                        val firebaseUser:FirebaseUser=task.result.user!!
                        //Storing the user in the fireStore database

                        //---------------------User--------------------------------------
                        val user=User(
                            firebaseUser.uid,
                            binding.etSignUpFirstName.text.toString().trim { it <= ' ' },
                            binding.etSignUpLastName.text.toString().trim { it <= ' ' },
                            email,
                            mobNo,
                            userGender,
                            binding.tvSignUpDob.text.toString().trim { it <= ' ' }
                            )
                        //--------Storing-------------------------
                        Log.v("RegisterNewUser","Registering User and Storing")
                        FireBaseUserDataBase().registerUser(this,user)
                    }
                }
            Toast.makeText(this, "SignUP clicked.............", Toast.LENGTH_SHORT).show()

        }
    }
}