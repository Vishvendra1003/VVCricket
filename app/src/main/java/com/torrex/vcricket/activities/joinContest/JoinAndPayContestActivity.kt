package com.torrex.vcricket.activities.joinContest

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.torrex.vcricket.R
import com.torrex.vcricket.activities.matches.MatchContestActivity
import com.torrex.vcricket.activities.payment.AddFundActivity
import com.torrex.vcricket.constants.DataBaseConstant
import com.torrex.vcricket.constants.GlobalConstant
import com.torrex.vcricket.constants.GlobalFunctions
import com.torrex.vcricket.databinding.ActivityJoinAndPayContestBinding
import com.torrex.vcricket.firebase.FireBaseContest
import com.torrex.vcricket.firebase.FireBasePaymentData
import com.torrex.vcricket.models.contests.MatchContest
import com.torrex.vcricket.models.payment.UserFund
import com.torrex.vcricket.modules.BaseActivity
import com.torrex.vcricket.sharedpreference.UserSharedPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JoinAndPayContestActivity : BaseActivity() {

    private lateinit var binding:ActivityJoinAndPayContestBinding
    private var mUserId=""
    private var mUserFund=0.0
    private var matchContest:MatchContest?=null
    private var teamSelected=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_join_and_pay_contest)

        CoroutineScope(Dispatchers.IO).launch {
            mUserId=UserSharedPreference(this@JoinAndPayContestActivity).getUserSharedPref().id
            FireBasePaymentData().getUserFund(this@JoinAndPayContestActivity,mUserId)
        }

        if (intent.hasExtra(GlobalConstant.CONTEST_TO_JOIN)){
            matchContest=intent.getParcelableExtra(GlobalConstant.CONTEST_TO_JOIN)
        }
        if (intent.hasExtra(GlobalConstant.CONTEST_TEAM_SELECTED_TO_JOIN)){
            teamSelected=intent.getIntExtra(GlobalConstant.CONTEST_TEAM_SELECTED_TO_JOIN,0)!!
        }

        if (teamSelected!=0 && matchContest!=null){
            if (teamSelected==1){
                binding.tvJoinPayTeamTitle.text= matchContest!!.contestTeam1Name
                binding.tvJoinAndPayContestPrice.text="Bet Price:${matchContest!!.contestBetPrice}"
                binding.tvJoinAndPayBetRatePrice.text=matchContest!!.contestTeam1BetPrice.toString()
                GlobalFunctions.loadUserPicture(this,matchContest!!.contestTeam1Img,binding.ivJoinAndPayTeamImage)
            }
            else{
                binding.tvJoinPayTeamTitle.text= matchContest!!.contestTeam2Name
                binding.tvJoinAndPayContestPrice.text="Bet Price:${matchContest!!.contestBetPrice}"
                binding.tvJoinAndPayBetRatePrice.text=matchContest!!.contestBetPrice.toString()
                binding.tvJoinAndPayBetRatePrice.text=matchContest!!.contestTeam2BetPrice.toString()
                GlobalFunctions.loadUserPicture(this,matchContest!!.contestTeam2Img,binding.ivJoinAndPayTeamImage)
            }
        }

        binding.btnPayAndJoinContest.setOnClickListener {
            val contestPrice=matchContest!!.contestBetPrice
            payAndJoinContest(contestPrice)
        }

    }

    fun getUserFundSuccess(userFund: UserFund){
        mUserFund =userFund.userFund
    }

    private fun payAndJoinContest(contestPrice:Int){
        if(mUserFund>contestPrice){
            showProgressDialog("Joining Contest")
            val totalFund=mUserFund-contestPrice
            FireBasePaymentData().updateUserFund(this,totalFund,mUserId)
        }
        else{
            val dialog=AlertDialog.Builder(this).setTitle(R.string.insufficient_balance_title)
                .setMessage(R.string.insufficient_balance_message)
            dialog.setCancelable(false)
            dialog.setPositiveButton("Ok"){dialogInterface,which->
                val intent=Intent(this,AddFundActivity::class.java)
                startActivity(intent)
            }
            dialog.setNegativeButton("Later"){dialogInterface,which->
                Log.v("DialogCancel","DialogFund Cancelled")
            }
            dialog.show()
        }

    }

    fun funUpdatedSuccessfully(){
        //TODO("Add transaction details for joining contest create new table for contest")
        val contestId=matchContest!!.contestId
        val seatLeft=matchContest!!.contestSeatLeft
        addBetToTeam(contestId,seatLeft)
    }

    private fun addBetToTeam(contestId:String,contestSeatLeft:Int) {
        val betRateHashMap=HashMap<String,Any>()
        val betRateTeam1=1.1
        val betRateTeam2=2.2
        val mContestSeatLeft = contestSeatLeft-1
        betRateHashMap[DataBaseConstant.BET_RATE_TEAM_1]=betRateTeam1
        betRateHashMap[DataBaseConstant.BET_RATE_TEAM_2]=betRateTeam2
        betRateHashMap[DataBaseConstant.BET_CONTEST_SEAT_LEFT]=mContestSeatLeft

        FireBaseContest().updateContestMatch(this,contestId,betRateHashMap)
    }

    fun updateContestMatchSuccess(){
        hideProgressDialog()
        finish()
    }
}