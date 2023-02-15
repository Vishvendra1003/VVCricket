package com.torrex.vcricket.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.torrex.vcricket.R

class SignInOptionActivity : AppCompatActivity() {

    private val mFirebaseAuth=FirebaseAuth.getInstance()
    private val authList:ArrayList<AuthUI.IdpConfig> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_option)

        signInUserMethod()
    }

    private fun signInResult(result: FirebaseAuthUIAuthenticationResult){
        val response=result.idpResponse
        if (result.resultCode== RESULT_OK){
            val user=mFirebaseAuth.currentUser
            Log.d("Auth","User: $response $user")
        }
        else{

        }
    }

    private fun signInUserMethod() {
        //ArrayList for the User method for signIn
        authList.add(AuthUI.IdpConfig.EmailBuilder().build())
        authList.add(AuthUI.IdpConfig.PhoneBuilder().build())

        //SignInIntent for the FirebaseUI--------------------
        val signInIntent=AuthUI.getInstance().createSignInIntentBuilder()
            .setAvailableProviders(authList).build()

        val signInLauncher=registerForActivityResult(
            FirebaseAuthUIActivityResultContract()){it ->
            this.signInResult(it)
        }
        signInLauncher.launch(signInIntent)
    }
}