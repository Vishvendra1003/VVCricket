package com.torrex.vcricket.activities.joinContest

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.torrex.vcricket.constants.GlobalFunctions
import com.torrex.vcricket.models.contests.MyJoinedContest
import com.torrex.vcricket.sharedpreference.MatchSharedPreference

class MyContestViewModel(private val context: Context):ViewModel() {

    private val _myJoinedContest=MutableLiveData<MyJoinedContest>()
    val myJoinedContest:LiveData<MyJoinedContest> =_myJoinedContest
    var teamSelected=MutableLiveData<String>("Team")
    var team1Img=MutableLiveData<String>()
    var team2Img=MutableLiveData<String>()




    fun getMyContestData(){
        val myContest=MatchSharedPreference(context).getSavedMyJoinedContestData()
        if (myContest!=null){
            _myJoinedContest.value=myContest
            team1Img.value=myContest.team1img
            team2Img.value=myContest.team2img

            val mTeamSelected=myContest.teamSelected
            if (mTeamSelected==1){
                teamSelected.value=myContest.team1Name
            }
            else{
                teamSelected.value=myContest.team2Name
            }
        }
    }

}