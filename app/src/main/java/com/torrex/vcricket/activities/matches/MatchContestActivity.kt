package com.torrex.vcricket.activities.matches

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.torrex.vcricket.R
import com.torrex.vcricket.constants.GlobalConstant
import com.torrex.vcricket.databinding.ActivityMatchContestBinding
import com.torrex.vcricket.models.currentMatch.Data
import com.torrex.vcricket.sharedpreference.MatchSharedPreference

class MatchContestActivity : AppCompatActivity() {

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


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}