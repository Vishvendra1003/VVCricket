package com.torrex.vcricket.activities.matches

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.torrex.vcricket.constants.GlobalFunctions
import com.torrex.vcricket.models.currentMatch.Data
import com.torrex.vcricket.models.currentMatch.TeamInfo

class MatchContestViewModel(private val context: Context,private val vMatch:Data):ViewModel() {

    var match= MutableLiveData<Data>(vMatch)
    var matchTeam1= MutableLiveData<TeamInfo>(vMatch.teamInfo[0])
    var matchTeam2= MutableLiveData<TeamInfo>(vMatch.teamInfo[1])
    var mDate =MutableLiveData<String>()

    init {
        val date=GlobalFunctions.getDateFromGMTString(vMatch.dateTimeGMT)
        mDate.value =date
    }

}