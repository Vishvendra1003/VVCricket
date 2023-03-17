package com.torrex.vcricket.firebase

import android.app.Activity
import android.util.Log
import com.torrex.vcricket.activities.adminAccount.adminContest.AddContestActivity
import com.torrex.vcricket.activities.joinContest.JoinAndPayContestActivity
import com.torrex.vcricket.activities.matches.MatchContestActivity
import com.torrex.vcricket.constants.DataBaseConstant
import com.torrex.vcricket.firebase.FirebaseStoreClass.mFireStore
import com.torrex.vcricket.models.contests.MatchContest

class FireBaseContest {

    //Save Contest Match
    fun saveContestMatch(activity:AddContestActivity,contest: MatchContest){
        mFireStore.collection(DataBaseConstant.MATCH_CONTEST)
            .add(contest)
            .addOnSuccessListener { it->
                val documentId=it.id
                mFireStore.collection(DataBaseConstant.MATCH_CONTEST)
                    .document(documentId).update(DataBaseConstant.CONTEST_ID,documentId)
                    .addOnSuccessListener {
                        activity.contestSavedSuccessfully()
                    }
            }
            .addOnFailureListener {
                Log.v("SAVE_CONTEST_ERROR",it.toString())
            }
    }

    //function to update match contest
    fun updateContestMatch(activity: Activity,contestId:String,betRateHashMap:HashMap<String,Any>){
        mFireStore.collection(DataBaseConstant.MATCH_CONTEST)
            .document(contestId)
            .update(betRateHashMap)
            .addOnSuccessListener {
                when(activity){

                    is JoinAndPayContestActivity->{
                        activity.updateContestMatchSuccess()
                    }
                }
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
                    is MatchContestActivity->{
                        activity.getContestListSuccess(contestList)
                    }
                }
            }
    }

    //GetContest Joined By User
    fun getJoinedMatchContest(activity:Activity,contestId:String){
        mFireStore.collection(DataBaseConstant.MATCH_CONTEST)
            .whereEqualTo(DataBaseConstant.CONTEST_ID,contestId)
            .get()
            .addOnSuccessListener { document->

            }
            .addOnFailureListener { e->
                Log.v("ERROR_GET_JOINED_MATCH",e.message.toString())
            }
    }
}