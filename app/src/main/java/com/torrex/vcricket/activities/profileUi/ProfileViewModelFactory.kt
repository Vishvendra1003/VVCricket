package com.torrex.vcricket.activities.profileUi

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.torrex.vcricket.models.User

class ProfileViewModelFactory(private val context: Context,private val user:User):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(context,user) as T
    }
}