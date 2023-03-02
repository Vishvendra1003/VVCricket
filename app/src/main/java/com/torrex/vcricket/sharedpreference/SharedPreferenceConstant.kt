package com.torrex.vcricket.sharedpreference

import com.google.firebase.auth.FirebaseUser
import com.torrex.vcricket.models.User

object SharedPreferenceConstant {

    val userSharedPref:String="USER_SHARED_PREF"
    var userId:String="USER_ID"
    val userName:String="USER_NAME"
    val userMobile:String="USER_NAME"
    val user: String="USER"

    //Matches information
    val matchSharedPreference:String="MATCH_SHARED_PREFERENCE"
    val currentMatchModelList:String="CURRENT_MATCH_MODEL_LIST"
    val currentMatchModel:String="CURRENT_MATCH_MODEL"
}