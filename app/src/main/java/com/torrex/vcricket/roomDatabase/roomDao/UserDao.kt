package com.torrex.vcricket.roomDatabase.roomDao

import androidx.room.*
import com.torrex.vcricket.models.User
import com.torrex.vcricket.roomDatabase.roomModels.VUser

@Dao
interface UserDao {

    //@Query("Select * from VUser")
    //suspend fun getAll():List<VUser>

    @Insert
    suspend fun insertAll(vUser:List<VUser>)

    @Insert
    suspend fun insertUser(vUser:VUser)
    @Delete
    suspend fun deleteUser(vUser:VUser)

    @Update
    suspend fun updateUser(vUser: VUser)


}