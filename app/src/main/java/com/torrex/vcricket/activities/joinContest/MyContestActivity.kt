package com.torrex.vcricket.activities.joinContest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.torrex.vcricket.R
import com.torrex.vcricket.constants.GlobalFunctions
import com.torrex.vcricket.databinding.ActivityMyContestBinding
import kotlinx.coroutines.CoroutineScope

class MyContestActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMyContestBinding
    private lateinit var myContestViewModel: MyContestViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_my_contest)
        myContestViewModel=ViewModelProvider(this,MyContestViewModelFactory(this))[MyContestViewModel::class.java]

        binding.myJoinedContestViewModel=myContestViewModel
        myContestViewModel.getMyContestData()
        binding.lifecycleOwner=this

        //Setting Of the Images in the Image View For the Team
        myContestViewModel.team1Img.observe(this, Observer {
            GlobalFunctions.loadUserPicture(this,it,binding.ivMyContestTeam1Image)
        })
        myContestViewModel.team2Img.observe(this, Observer {
            GlobalFunctions.loadUserPicture(this,it,binding.ivMyContestTeam2Image)
        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}