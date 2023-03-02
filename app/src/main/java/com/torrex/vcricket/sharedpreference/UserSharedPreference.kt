package com.torrex.vcricket.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.util.Log
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson
import com.torrex.vcricket.models.User

open class UserSharedPreference(context: Context) {
    private var mSharedPreference: SharedPreferences? =null
    private var mPrefEditor: Editor?=null
    val gson=Gson()


    init {
        if (mSharedPreference == null) {
            mSharedPreference = context.getSharedPreferences(SharedPreferenceConstant.userSharedPref, Context.MODE_PRIVATE)
            mPrefEditor = mSharedPreference!!.edit()
        }
        else{
            mPrefEditor = mSharedPreference!!.edit()
        }

    }

    //set userId for the user in shared Pref
    fun setMUserId(userId: String) {
        mPrefEditor!!.putString(SharedPreferenceConstant.userId, userId.toString())
        mPrefEditor!!.apply()
        //Log.v("UserSharedPreference",mSharedPreference.getString(SharedPreferenceConstant.userId,null)!!)
    }

    //get userId from shared pref
    fun getMUserId():String{
        return mSharedPreference!!.getString(SharedPreferenceConstant.userId,"")!!
    }


    fun setUserSharedPref(user:User) {
        val jsonUser =gson.toJson(user)
        mPrefEditor!!.putString(SharedPreferenceConstant.user, jsonUser)
        mPrefEditor!!.apply()

    }

    fun getUserSharedPref():User{
        val jsonUser= mSharedPreference!!.getString(SharedPreferenceConstant.user,null)
        val mUser=gson!!.fromJson(jsonUser,User::class.java)
        return mUser
    }


}