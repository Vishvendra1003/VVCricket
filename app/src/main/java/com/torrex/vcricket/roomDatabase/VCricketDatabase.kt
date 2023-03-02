package com.torrex.vcricket.roomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.torrex.vcricket.roomDatabase.roomDao.UserDao
import com.torrex.vcricket.roomDatabase.roomModels.VUser

@Database(entities = [VUser::class], version = 1)

abstract class VCricketDatabase :RoomDatabase(){
    abstract fun VUserDao():UserDao
}