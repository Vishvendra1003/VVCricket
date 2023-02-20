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
import com.torrex.vcricket.constants.GlobalValueConstant
import com.torrex.vcricket.firebase.FireBaseUserDataBase
import com.torrex.vcricket.models.User
import com.torrex.vcricket.modules.BaseActivity
import kotlinx.android.synthetic.main.activity_register_new_user.*

class RegisterNewUser : BaseActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_new_user)

        setUpActionBar()

        //Click Functionality
        btn_signUp.setOnClickListener(this)
        ib_date_picker.setOnClickListener(this)
        iv_profile_register_image.setOnClickListener(this)
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
        setSupportActionBar(toolbar_register_activity)

        val actionBar=supportActionBar
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        }
        toolbar_register_activity.setNavigationOnClickListener{onBackPressed()}
    }

    //Validate the user details entered
    private fun validateUserDetails():Boolean{
        return when{
            TextUtils.isEmpty(et_signup_email.text.toString().trim{it<=' '})->{
                showErrorSnackBar("Please Enter Email",true)
                false
            }

            TextUtils.isEmpty(et_signUp_first_name.text.toString().trim { it<=' '})->{
                showErrorSnackBar("Please Enter First Name",true)
                false
            }
            TextUtils.isEmpty(et_signUp_last_name.text.toString().trim { it<=' '})->{
                showErrorSnackBar("Please Enter Last Name",true)
                false
            }

            TextUtils.isEmpty(tv_signUp_dob.text.toString().trim{it<=' '})-> {
                showErrorSnackBar("Please Enter DOB",true)
                false
            }

            TextUtils.isEmpty(et_signUp_mobile.text.toString().trim { it<=' '})->{
                showErrorSnackBar("Please Enter Correct MobileNo",true)
                false
            }
            et_signUp_mobile.text.toString().trim { it<=' '}.length!=10->{
                showErrorSnackBar("Please Enter Correct MobileNo",true)
                false
            }
            et_signUp_password.text.toString().trim {it<=' '}!=et_signUp_confirm_password.text.toString().trim {it<=' '}->{
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
                    datePickerCustom(tv_signUp_dob)
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
            val email=et_signup_email.text.toString().trim{it <=' '}
            val password = et_signUp_password.text.toString().trim { it <= ' ' }
            var userGender: String = ""
            var mobNo=et_signUp_mobile.text.toString().trim { it <= ' ' }.toLong()

            if (rb_male.isChecked){
                userGender=GlobalValueConstant.MALE
            }
            else{
                userGender=GlobalValueConstant.FEMALE
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
                            et_signUp_first_name.text.toString().trim { it <= ' ' },
                            et_signUp_last_name.text.toString().trim { it <= ' ' },
                            email,
                            mobNo,
                            userGender,
                            tv_signUp_dob.text.toString().trim { it <= ' ' }
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