package com.torrex.vcricket.activities.matches

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.torrex.vcricket.models.currentMatch.Data

class MatchContestViewModelFactory(private val context: Context,private val vMatch:Data):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MatchContestViewModel(context,vMatch) as T
    }
}