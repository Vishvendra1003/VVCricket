package com.torrex.vcricket.firebase

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceIdReceiver
import com.google.firebase.messaging.FirebaseMessaging

object FirebaseStoreClass {

        val mFireStore=FirebaseFirestore.getInstance()
}

class FirebaseGlobalFunction{

    fun getCurrentUserId():String{
        val mCurrentUser=FirebaseAuth.getInstance().currentUser
        var mCurrentUserId=""
        if (mCurrentUser!=null){
            mCurrentUserId=mCurrentUser!!.uid
        }
        return mCurrentUserId

    }

    fun getRefreshedToken():String{
        var token=""
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task->
            if (!task.isSuccessful){
                Log.v("FIREBASE_TOKEN","Firebase token Failed ${task.exception!!.message.toString()}")
            }
            //New FCM Token
            token=task.result
            Log.v("FIREBASE_TOKEN","Token: ${token}")

        })
        return token
    }

}