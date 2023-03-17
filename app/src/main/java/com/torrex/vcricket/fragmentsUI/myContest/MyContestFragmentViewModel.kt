package com.torrex.vcricket.fragmentsUI.myContest

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.torrex.vcricket.constants.GlobalFunctions
import com.torrex.vcricket.models.contests.MatchContest
import com.torrex.vcricket.models.contests.MyJoinedContest

class MyContestFragmentViewModel(private val context: Context) : ViewModel() {

    var contestList=MutableLiveData<ArrayList<MyJoinedContest>>()

}