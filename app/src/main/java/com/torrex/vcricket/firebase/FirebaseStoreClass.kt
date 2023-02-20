package com.torrex.vcricket.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseStoreClass {

        val mFireStore=FirebaseFirestore.getInstance()

        //Querying for the Current User--------------------
        fun getCurrentUserId():String{
            val mCurrentUser=FirebaseAuth.getInstance().currentUser
            var mCurrentUserId=""
            if (mCurrentUser!=null){
                mCurrentUserId=mCurrentUser!!.uid
            }
            return mCurrentUserId

        }

}