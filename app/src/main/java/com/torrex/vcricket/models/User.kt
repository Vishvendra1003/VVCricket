package com.torrex.vcricket.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id:String="",
    val firstName:String="",
    val lastName:String="",
    var email:String="",
    val mobile:Long=0,
    val gender:String="",
    val dob:String="",
    val image:String="",
    val profileCompleted:Int=0,
    var userAccount:Boolean=false
):Parcelable
