package com.torrex.vcricket.roomDatabase.roomModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "VUserTable")
data class VUser(
    @PrimaryKey(autoGenerate = true)
    val userId:Int,
    val firebaseId:String?,
    val firstName:String?,
    val lastName:String?,
    val email:String?,
    val mobile:Long=0,
    val gender:String?,
    val dob:String?,
    val image:String?
)

