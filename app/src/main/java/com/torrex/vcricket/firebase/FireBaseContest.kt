package com.torrex.vcricket.firebase

import android.app.Activity
import android.util.Log
import com.torrex.vcricket.activities.adminAccount.adminContest.AddContestActivity
import com.torrex.vcricket.constants.DataBaseConstant
import com.torrex.vcricket.firebase.FirebaseStoreClass.mFireStore
import com.torrex.vcricket.models.contests.MatchContest

class FireBaseContest {

    //Save Contest Match
    fun saveContestMatch(activity:AddContestActivity,contest: MatchContest){
        mFireStore.collection(DataBaseConstant.MATCH_CONTEST)
            .add(contest)
            .addOnSuccessListener { it->
                activity.contestSavedSuccessfully()
            }
            .addOnFailureListener {
                Log.v("SAVE_CONTEST_ERROR",it.toString())
            }
    }

    //Get Contest Match
    fun getContestMatch(activity:Activity,matchId:String){
        mFireStore.collection(DataBaseConstant.MATCH_CONTEST)
            .whereEqualTo(DataBaseConstant.MATCH_ID,matchId)
            .get()
            .addOnSuccessListener { document->
                val contestList:ArrayList<MatchContest> = ArrayList()
                Log.v("MATCH_CONTEST_LIST",document.toString())

                for (i in document.documents){
                    val matchContest=i.toObject(MatchContest::class.java)
                    contestList.add(matchContest!!)
                }
                when(activity){
                    is AddContestActivity->{
                        activity.getContestListSuccess(contestList)
                    }
                }
            }
    }
}