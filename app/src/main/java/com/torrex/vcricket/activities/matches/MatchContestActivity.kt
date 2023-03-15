package com.torrex.vcricket.activities.matches

import android.content.Intent
import android.os.BaseBundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.torrex.vcricket.R
import com.torrex.vcricket.activities.joinContest.JoinAndPayContestActivity
import com.torrex.vcricket.constants.GlobalConstant
import com.torrex.vcricket.databinding.ActivityMatchContestBinding
import com.torrex.vcricket.firebase.FireBaseContest
import com.torrex.vcricket.models.contests.MatchContest
import com.torrex.vcricket.models.currentMatch.Data
import com.torrex.vcricket.modules.BaseActivity
import com.torrex.vcricket.sharedpreference.MatchSharedPreference

class MatchContestActivity : BaseActivity() {

    private lateinit var binding: ActivityMatchContestBinding
    private lateinit var matchContestViewModel: MatchContestViewModel
    private var vMatch:Data?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_match_contest)

        vMatch=MatchSharedPreference(this).getCurrentMatchOffLine()

        matchContestViewModel=ViewModelProvider(this,MatchContestViewModelFactory(this,vMatch!!))[MatchContestViewModel::class.java]
        binding.matchContestViewModel=matchContestViewModel
        binding.lifecycleOwner=this

        setUpContestListRecycleView()

    }

    private fun setUpContestListRecycleView() {
        showProgressDialog("Loading!!!!!!!!!!!!")
        FireBaseContest().getContestMatch(this,vMatch!!.id)

    }

    fun getContestListSuccess(contestList:ArrayList<MatchContest>){

        if (contestList.size>0){
            val recyclerView=binding.rvMatchContestList
            recyclerView.layoutManager=LinearLayoutManager(this)
            recyclerView.setHasFixedSize(true)

            val adapter=MatchContestRecycleViewAdapter(this,contestList)
            recyclerView.adapter=adapter

            adapter.setOnClickListener(object:MatchContestRecycleViewAdapter.SetOnClickListener{
                override fun onItemClicked(position: Int, currentMatchModel: MatchContest, teamSelected: Int) {
                    val intent= Intent(this@MatchContestActivity,JoinAndPayContestActivity::class.java)
                    intent.putExtra(GlobalConstant.CONTEST_TO_JOIN,currentMatchModel)
                    intent.putExtra(GlobalConstant.CONTEST_TEAM_SELECTED_TO_JOIN,teamSelected)
                    startActivity(intent)
                }
            })
        }
        hideProgressDialog()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.v("MatchContestActivity:","OnBackPressed")
        finish()
    }

    override fun onResume() {
        super.onResume()
        Log.v("MatchContestActivity:","OnResumed")
    }

    override fun onRestart() {
        super.onRestart()
        Log.v("MatchContestActivity:","OnRestart")
        setUpContestListRecycleView()
    }


}