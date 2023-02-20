package com.torrex.vcricket.firebase

import android.app.Activity
import android.util.Log
import com.google.firebase.firestore.SetOptions
import com.torrex.vcricket.activities.mainUi.RegisterNewUser
import com.torrex.vcricket.activities.mainUi.LoginActivity
import com.torrex.vcricket.activities.mainUi.MainActivity
import com.torrex.vcricket.activities.mainUi.RegisterWithPhoneActivity
import com.torrex.vcricket.constants.DataBaseConstant
import com.torrex.vcricket.firebase.FirebaseStoreClass.mFireStore
import com.torrex.vcricket.models.User

class FireBaseUserDataBase {


    //Registering new User to the Database------------------------
    fun registerUser(activity: Activity, user: User){

        mFireStore.collection(DataBaseConstant.USERS)
            .document(user.id)
            .set(user, SetOptions.merge())
            .addOnSuccessListener {
                when(activity){
                    is RegisterNewUser->{
                        activity.userRegisteredSuccessfully(user)
                    }
                    is RegisterWithPhoneActivity->{
                        activity.userRegisteredSuccessWithPhone(user)
                    }
                }
            }

            .addOnFailureListener { e->
                Log.e(activity.javaClass.simpleName,"error while registering the new user from Register User class",e)

            }
    }

    //Get User from the Firebase Store Database
    fun getUserFireStore(activity:Activity,userId:String){
        mFireStore.collection(DataBaseConstant.USERS)
            .document(userId)
            .get()
            .addOnSuccessListener { document->
                Log.v(activity.javaClass.simpleName,document.toString())

                //converting document to user
                val mUser=document.toObject(User::class.java)!!

                when(activity){
                    is LoginActivity->{
                        activity.userLoggedInSuccess(mUser)
                    }
                    is MainActivity->{
                        activity.getUserSuccess(mUser)
                    }
                }
            }
    }
}