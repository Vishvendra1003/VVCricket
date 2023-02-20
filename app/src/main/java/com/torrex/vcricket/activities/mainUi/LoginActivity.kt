package com.torrex.vcricket.activities.mainUi

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.torrex.vcricket.R
import com.torrex.vcricket.constants.GlobalConstant
import com.torrex.vcricket.models.User
import com.torrex.vcricket.modules.BaseActivity
import com.torrex.vcricket.sharedpreference.UserSharedPreference
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(),View.OnClickListener {
    private val mFirebaseAuth=FirebaseAuth.getInstance()
    val LOGIN_INTENT_CODE=1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener(this)
        tv_sign_up.setOnClickListener(this)
        iv_login_with_phone.setOnClickListener(this)


    }

    //Functionality for the click event
    override fun onClick(v: View?) {
        if (v!=null){
            when(v.id){
                R.id.btn_login->{
                    if (validateDetails()){
                        showProgressDialog("Logging !!!!!")
                        loginRegisteredUser()
                    }
                    Toast.makeText(this,"Login Clicked",Toast.LENGTH_SHORT).show()

                }
                R.id.tv_sign_up->{
                    startActivity(Intent(this, RegisterNewUser::class.java))
                    Toast.makeText(this,"sign up clicked",Toast.LENGTH_SHORT).show()

                }
                R.id.iv_login_with_phone->{
                    Toast.makeText(this,"Phone clicked",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, SignInOptionActivity::class.java))

                }
            }
        }
    }

    //checking for the user registered
    private fun loginRegisteredUser() {
        val email=et_login_email.text.toString().trim(){it<=' '}
        val password=et_login_password.text.toString().trim(){it <=' '}

        //Log in user from the fireBase Auth Email
        mFirebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task->
                if (task.isSuccessful){
                    val userPref=UserSharedPreference(this)
                    userPref.setMUserId(mFirebaseAuth.currentUser!!)
                    //TODO("SetUpUser Details after login")
                    Log.v("LoginActivity",mFirebaseAuth.currentUser!!.toString())
                    Toast.makeText(this,"Logged In Success!!!", Toast.LENGTH_LONG).show()

                    val userId=userPref.getMUserId()
                    Log.v("LoginActivity",userId.toString())

                }
            }
    }

    //Method to check the required field filled or not
    private fun validateDetails():Boolean{
        return when{
            TextUtils.isEmpty(et_login_email.text.toString().trim { it <= ' ' }) ->{
                //TODO("error sneakbar")
                Toast.makeText(this,"enter email", Toast.LENGTH_LONG).show()
                false
            }
            TextUtils.isEmpty(et_login_password.text.toString().trim { it<=' ' })->{
                //TODO("error sneakbar")
                Toast.makeText(this,"enter password", Toast.LENGTH_LONG).show()
                false
            }
            else->{
                true
            }
        }
    }

    //after User Logged in Successfully
    fun userLoggedInSuccess(user:User){
        //userPref.setUserSharedPref(user)
        hideProgressDialog()
        finish()
        val intent=Intent(this,MainActivity::class.java)
        intent.putExtra(GlobalConstant.USER_MODEL_DATA,user)
        startActivity(intent)

    }


}