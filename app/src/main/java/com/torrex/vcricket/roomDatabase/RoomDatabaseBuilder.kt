package com.torrex.vcricket.roomDatabase

import android.content.Context
import androidx.room.Room

object RoomDatabaseBuilder {

    private var INSTANCE:VCricketDatabase?=null
    const val V_Cricket_Database="VCricketDatabase"
    fun getInstance(context:Context):VCricketDatabase{
        if (INSTANCE==null){
            synchronized(this){
                INSTANCE= Room.databaseBuilder(context.applicationContext,
                    VCricketDatabase::class.java, V_Cricket_Database).build()
            }
        }
        return INSTANCE!!
    }


}