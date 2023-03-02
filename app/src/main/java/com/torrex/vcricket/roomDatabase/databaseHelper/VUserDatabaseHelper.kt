package com.torrex.vcricket.roomDatabase.databaseHelper

import com.torrex.vcricket.models.User
import com.torrex.vcricket.roomDatabase.VCricketDatabase
import com.torrex.vcricket.roomDatabase.roomDao.UserDao
import com.torrex.vcricket.roomDatabase.roomModels.VUser

class VUserDatabaseHelper(private val vCricketDatabase: VCricketDatabase){

    //suspend fun getVUser(): List<User> =vCricketDatabase.VUserDao().getAll()

    suspend fun insertAll(vUser: List<VUser>) =vCricketDatabase.VUserDao().insertAll(vUser)

    suspend fun deleteUser(vUser: VUser) =vCricketDatabase.VUserDao().deleteUser(vUser)

    suspend fun saveUser(vUser:VUser) =vCricketDatabase.VUserDao().insertUser(vUser)
}