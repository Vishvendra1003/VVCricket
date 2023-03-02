package com.torrex.vcricket.roomDatabase.roomDao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
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


}