package com.torrex.vcricket.activities.profileUi

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.torrex.vcricket.models.User

class ProfileViewModel(private val context: Context,user:User): ViewModel() {
    private var mUser=MutableLiveData<User>()
    val mUserLiveData:LiveData<User> get() =mUser

    init {
        mUser.value=user
    }

    fun saveContact(){

    }


}