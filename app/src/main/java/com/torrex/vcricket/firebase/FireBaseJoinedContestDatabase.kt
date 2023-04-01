package com.torrex.vcricket.firebase

import android.app.Activity
import android.util.Log
import com.google.firebase.firestore.ktx.toObject
import com.torrex.vcricket.activities.joinContest.JoinAndPayContestActivity
import com.torrex.vcricket.constants.DataBaseConstant
import com.torrex.vcricket.firebase.FirebaseStoreClass.mFireStore
import com.torrex.vcricket.fragmentsUI.myContest.MyContestFragment
import com.torrex.vcricket.models.contests.BetContestModel
import com.torrex.vcricket.models.contests.MatchContest
import com.torrex.vcricket.models.contests.MyJoinedContest

class FireBaseJoinedContestDatabase {

    fun saveJoinedContest(activity:JoinAndPayContestActivity,myJoinedContest: MyJoinedContest){
        mFireStore.collection(DataBaseConstant.MY_JOINED_CONTEST)
            .add(myJoinedContest)
            .addOnSuccessListener {
                val documentId=it.id
                mFireStore.collection(DataBaseConstant.MY_JOINED_CONTEST)
                    .document(documentId)
                    .update(DataBaseConstant.JOINED_CONTEST_ID,documentId)
                    .addOnSuccessListener {
                        activity.joinedContestSavedSuccessfully(documentId)
                    }

            }
            .addOnFailureListener { e->
                Log.v("JOINED_CONTEST_SAVE",e.message.toString())
            }
    }

    fun getJoinedContest(fragment:MyContestFragment,userId:String){
        mFireStore.collection(DataBaseConstant.MY_JOINED_CONTEST)
            .whereEqualTo(DataBaseConstant.USER_JOINED_ID,userId)
            .get()
            .addOnSuccessListener { document->
                val joinedContestList=ArrayList<MyJoinedContest>()
                for(i in document.documents){
                    val joinedContest=i.toObject(MyJoinedContest::class.java)
                    joinedContestList.add(joinedContest!!)
                }
                fragment.getContestListSuccess(joinedContestList)

            }
            .addOnFailureListener { e->
                Log.v("ERROR_GET_CONTEST",e.message.toString())
            }
    }

    fun getJoinedContest(activity:Activity,contestId:String){
        mFireStore.collection(DataBaseConstant.MY_JOINED_CONTEST)
            .whereEqualTo(DataBaseConstant.CONTEST_ID_JOINED_CONTEST_ID,contestId)
            .get()
            .addOnSuccessListener { document->
                val joinedContestList=ArrayList<MyJoinedContest>()
                for(i in document.documents){
                    val joinedContest=i.toObject(MyJoinedContest::class.java)
                    joinedContestList.add(joinedContest!!)
                }
                when(activity){
                    is JoinAndPayContestActivity->
                        activity.getContestListSuccess(joinedContestList)
                }


            }
            .addOnFailureListener { e->
                Log.v("ERROR_GET_CONTEST",e.message.toString())
            }
    }
}