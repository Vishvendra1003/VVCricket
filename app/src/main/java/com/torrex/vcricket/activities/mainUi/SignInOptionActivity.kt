package com.torrex.vcricket.activities.mainUi

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.torrex.vcricket.R
import com.torrex.vcricket.constants.GlobalConstant
import com.torrex.vcricket.constants.GlobalValueConstant

class SignInOptionActivity() : AppCompatActivity() {

    private val mFirebaseAuth=FirebaseAuth.getInstance()
    private val authList:ArrayList<AuthUI.IdpConfig> = ArrayList()
    private var mPhone:String=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_option)

            signInUserMethod()
    }

    private fun signInResult(result: FirebaseAuthUIAuthenticationResult){
        val response=result.idpResponse
        if (result.resultCode== RESULT_OK){
            val userId=mFirebaseAuth.currentUser!!.uid
            val userPhone=response!!.phoneNumber!!.takeLast(10)
            val isNewUser=response.isNewUser
            if (isNewUser){
                finish()
                val intent=Intent(this,RegisterWithPhoneActivity::class.java)
                intent.putExtra(GlobalConstant.USER_PHONE_DATA,userPhone)
                intent.putExtra(GlobalConstant.USER_ID_PHONE_SIGNIN,userId)
                startActivity(intent)
            }
            else{
                finish()
                val intent=Intent(this,MainActivity::class.java)
                intent.putExtra(GlobalConstant.USER_PHONE_DATA,userPhone)
                intent.putExtra(GlobalConstant.USER_ID_PHONE_SIGNIN,userId)
                startActivity(intent)

            }
            Log.d("Auth","User: $response $userId")

        }
        else{
            Log.d(this.javaClass.simpleName,Exception().toString())
        }
    }

    private fun signInUserMethod() {
        //ArrayList for the User method for signIn
        //authList.add(AuthUI.IdpConfig.EmailBuilder().build())
        authList.add(AuthUI.IdpConfig.PhoneBuilder().build())

        //SignInIntent for the FirebaseUI--------------------
        val signInIntent=AuthUI.getInstance().createSignInIntentBuilder()
            .setAvailableProviders(authList)
            .setTheme(R.style.Theme_VCricket_NoActionBar)
            .setLogo(R.drawable.ic_baseline_home_24)
            .build()


        val signInLauncher=registerForActivityResult(
            FirebaseAuthUIActivityResultContract()){it ->
            this.signInResult(it)
        }
        signInLauncher.launch(signInIntent)
    }
}