package com.torrex.vcricket.activities.adminAccount.adminContest

import android.app.Activity
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.torrex.vcricket.firebase.FireBaseContest
import com.torrex.vcricket.models.contests.MatchContest
import com.torrex.vcricket.models.currentMatch.Data
import com.torrex.vcricket.models.currentMatch.TeamInfo
import com.torrex.vcricket.sharedpreference.MatchSharedPreference

class AddContestViewModel(private val context: Context):ViewModel() {
    var contestMatch= MutableLiveData<Data>()
    var team1=MutableLiveData<TeamInfo>()
    var team2=MutableLiveData<TeamInfo>()
    var betPriceList=MutableLiveData<ArrayList<MatchContest>>()
    var contestList=MutableLiveData<ArrayList<MatchContest>>()



    fun getCurrentMatchDetails(){
        val currentMatch= MatchSharedPreference(context).getCurrentMatchOffLine()
        contestMatch.value=currentMatch
        team1.value=currentMatch.teamInfo[0]
        team2.value=currentMatch.teamInfo[1]
        FireBaseContest().getContestMatch(context as Activity,currentMatch.id)
    }

    fun submitContest(betPrice:Int,contestSeat:Int){
        val matchContest=MatchContest(contestMatch.value!!.id,
        contestMatch.value!!.name,team1.value!!.img,team2.value!!.img,team1.value!!.name,team2.value!!.name,
        1.0,1.0,contestSeat,contestSeat,betPrice)

        FireBaseContest().saveContestMatch(context as AddContestActivity,matchContest)
    }
}