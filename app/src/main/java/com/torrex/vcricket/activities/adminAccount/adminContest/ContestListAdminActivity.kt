package com.torrex.vcricket.activities.adminAccount.adminContest

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.torrex.vcricket.R
import com.torrex.vcricket.databinding.ActivityContestListAdminBinding
import com.torrex.vcricket.fragmentsUI.home.HomeMatchAdapter
import com.torrex.vcricket.models.currentMatch.CurrentMatchModel
import com.torrex.vcricket.models.currentMatch.Data
import com.torrex.vcricket.modules.BaseActivity
import com.torrex.vcricket.sharedpreference.MatchSharedPreference

class ContestListAdminActivity : BaseActivity() {

    private var currentMatchList:CurrentMatchModel?=null
    private lateinit var binding:ActivityContestListAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_contest_list_admin)
        getMatchListFromStorage()
        binding.lifecycleOwner=this

    }


    private fun setUpRecycleViewList(matchList:CurrentMatchModel){
        if (matchList.data.isNotEmpty()){
            val contestMatchListRecycleView=binding.rvMatchContestListAdmin
            contestMatchListRecycleView.layoutManager=LinearLayoutManager(this)
            contestMatchListRecycleView.setHasFixedSize(true)

            val adapter=HomeMatchAdapter(this,matchList)
            contestMatchListRecycleView.adapter=adapter
            //hideProgressDialog()
            adapter.setOnClickListener(object :HomeMatchAdapter.ItemClickListener{
                override fun onItemClicked(position: Int, currentMatchModel: Data) {
                    MatchSharedPreference(this@ContestListAdminActivity).saveCurrentMatchOffLine(currentMatchModel)
                    val intent=Intent(this@ContestListAdminActivity, AddContestActivity::class.java)
                    startActivity(intent)
                }
            })
            }
        }

    private fun getMatchListFromStorage(){
        //showProgressDialog("Loading Matches")
        currentMatchList=MatchSharedPreference(this).getCurrentMatchListOffLine()
        setUpRecycleViewList(currentMatchList!!)
    }

    override fun onResume() {
        super.onResume()
        getMatchListFromStorage()
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }


}