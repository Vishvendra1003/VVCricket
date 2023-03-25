package com.torrex.vcricket.activities.joinContest

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyContestViewModelFactory(private val context: Context):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MyContestViewModel(context) as T
    }
}