package com.torrex.vcricket.activities.mainUi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.torrex.vcricket.R
import com.torrex.vcricket.constants.GlobalConstant
import com.torrex.vcricket.databinding.ActivitySignInOptionBinding
import com.torrex.vcricket.firebase.FireBaseUserDataBase
import com.torrex.vcricket.firebase.FirebaseStoreClass
import com.torrex.vcricket.models.User
import com.torrex.vcricket.roomDatabase.RoomDatabaseBuilder
import com.torrex.vcricket.roomDatabase.VCricketDatabase
import com.torrex.vcricket.roomDatabase.databaseHelper.VUserDatabaseHelper
import com.torrex.vcricket.roomDatabase.roomModels.VUser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignInOptionActivity() : AppCompatActivity() {

    private lateinit var binding:ActivitySignInOptionBinding
    private lateinit var db: VCricketDatabase

    private val mFirebaseAuth=FirebaseAuth.getInstance()
    private val authList:ArrayList<AuthUI.IdpConfig> = ArrayList()
    private var signInLauncher=registerForActivityResult(
        FirebaseAuthUIActivityResultContract()){it ->
        this.signInResult(it)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignInOptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignInWithPhone.setOnClickListener{
            signInUserMethod()
        }
        binding.ivSignInWithPhone.setOnClickListener{
            signInUserMethod()
        }


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
                FireBaseUserDataBase().getUserFireStore(this,userId)
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

        signInLauncher.launch(signInIntent)
    }

    //Get User Success
    fun getUserSuccess(user:User){
        //check for User Updated or added------------------
        if (user!=null){
            val vUser= VUser(0,user.id,user.firstName,user.lastName,user.email,
                user.mobile,user.gender,user.dob,user.image)
            saveUserToDatabase(vUser)
            Log.v("USER_SAVED_IN_DATABASE","User saved")
        }
        finish()
        val intent=Intent(this,MainActivity::class.java)
        intent.putExtra(GlobalConstant.USER_ADDED_UPDATED,true)
        startActivity(intent)

    }
    private fun saveUserToDatabase(user: VUser) {
        db= RoomDatabaseBuilder.getInstance(this)
        GlobalScope.launch(){
            VUserDatabaseHelper(db).saveUser(user)
            //TODO("change user to vUser Type for roomdatabase")
        }
    }


}