package com.torrex.vcricket.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.torrex.vcricket.models.contests.MyJoinedContest
import com.torrex.vcricket.models.currentMatch.CurrentMatchModel
import com.torrex.vcricket.models.currentMatch.Data

open class MatchSharedPreference(private val context: Context) {
    private var mSharedPreference:SharedPreferences?=null
    private var mPrefEditor: SharedPreferences.Editor?=null
    private val gson=Gson()

    init {
        if (mSharedPreference == null) {
            mSharedPreference = context.getSharedPreferences(SharedPreferenceConstant.matchSharedPreference, Context.MODE_PRIVATE)
            mPrefEditor = mSharedPreference!!.edit()
        }
        else{
            mPrefEditor = mSharedPreference!!.edit()
        }
    }

    fun saveCurrentMatchListOffLine(currentMatchModelList:CurrentMatchModel){
        val mCurrentMatchList=gson.toJson(currentMatchModelList)
        mPrefEditor!!.putString(SharedPreferenceConstant.currentMatchModelList,mCurrentMatchList)
        mPrefEditor!!.apply()
    }

    fun getCurrentMatchListOffLine():CurrentMatchModel{
        val mCurrentMatchList=mSharedPreference!!.getString(SharedPreferenceConstant.currentMatchModelList,null)
        return gson.fromJson(mCurrentMatchList,CurrentMatchModel::class.java)
    }

    fun saveCurrentMatchOffLine(currentMatchModel:Data){
        val mCurrentMatch=gson.toJson(currentMatchModel)
        mPrefEditor!!.putString(SharedPreferenceConstant.currentMatchModel,mCurrentMatch)
        mPrefEditor!!.apply()
    }

    fun getCurrentMatchOffLine():Data{
        val mCurrentMatch=mSharedPreference!!.getString(SharedPreferenceConstant.currentMatchModel,null)
        return gson.fromJson(mCurrentMatch,Data::class.java)
    }

    fun saveMyJoinedContest(myJoinedContest: MyJoinedContest){
        val mJoinedContest=gson.toJson(myJoinedContest)
        mPrefEditor!!.putString(SharedPreferenceConstant.myJoinedContestData,mJoinedContest)
        mPrefEditor!!.apply()
    }

    fun getSavedMyJoinedContestData():MyJoinedContest{
        val mMyJoinedContestData=mSharedPreference!!.getString(SharedPreferenceConstant.myJoinedContestData,null)
        Log.v("MY_JOINED_CONTEST",mMyJoinedContestData.toString())

        return gson.fromJson(mMyJoinedContestData,MyJoinedContest::class.java)
    }
}