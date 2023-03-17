package com.torrex.vcricket.activities.joinContest

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.databinding.DataBindingUtil
import com.torrex.vcricket.R
import com.torrex.vcricket.activities.payment.AddFundActivity
import com.torrex.vcricket.constants.DataBaseConstant
import com.torrex.vcricket.constants.GlobalConstant
import com.torrex.vcricket.constants.GlobalFunctions
import com.torrex.vcricket.databinding.ActivityJoinAndPayContestBinding
import com.torrex.vcricket.firebase.FireBaseContest
import com.torrex.vcricket.firebase.FireBaseJoinedContestDatabase
import com.torrex.vcricket.firebase.FireBasePaymentData
import com.torrex.vcricket.fragmentsUI.payment.PaymentFragment
import com.torrex.vcricket.models.contests.MatchContest
import com.torrex.vcricket.models.contests.MyJoinedContest
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
        setUpActionBar()
    }

    private fun setUpActionBar() {
        setSupportActionBar(binding.toolbarJoinAndPay)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    //Check for userFund
    fun getUserFundSuccess(userFund: UserFund){
        mUserFund =userFund.userFund
        binding.tvUserFund.text="Rs: ${mUserFund}"
    }

    //Pay for contest and Join on Click
    private fun payAndJoinContest(contestPrice:Int){
        if(mUserFund>contestPrice){
            //Dialog to show for joining Contest
            val dialog=AlertDialog.Builder(this).setTitle(R.string.confirm_to_join_contest_title)
                .setMessage(R.string.confirm_to_join_contest)
            dialog.setCancelable(false)
            //Dialog Positive
            dialog.setPositiveButton("Confirm"){dialogInterface,which->
                showProgressDialog("Joining Contest")
                val totalFund=mUserFund-contestPrice
                FireBasePaymentData().updateUserFund(this,totalFund,mUserId)
            }
            //Dialog Negative
            dialog.setNegativeButton("Cancel"){dialogInterface,which->
                Log.v("DialogCancel","DialogFund Cancelled")
            }
            dialog.show()

        }
        else{
            //Dialog for Insufficient Balance
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

    //Fund to be updated in the database
    fun funUpdatedSuccessfully(){
        //TODO("Add transaction details for joining contest create new table for contest")
        val team1Score:String=""
        val team2Score:String=""
        val matchStatus:String="Finished"
        val userWinStatus:String="WON"
        //TODO("Score Functionality for the team")
        val myJoinedContest=MyJoinedContest(
            matchContest!!.matchId,matchContest!!.contestId,
            matchContest!!.contestName, matchContest!!.matchTime,
            matchContest!!.contestTeam1Name,matchContest!!.contestTeam2Name,
            matchContest!!.contestTeam1Img,matchContest!!.contestTeam2Img,
            team1Score,team2Score,
            teamSelected,matchContest!!.contestBetPrice,
            matchContest!!.contestTeam1BetPrice,matchContest!!.contestTeam2BetPrice,
            matchStatus,mUserId,userWinStatus)

        FireBaseJoinedContestDatabase().saveJoinedContest(this,myJoinedContest)
    }

    //Adding the bet Placed for the contest
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

    //Contest placed successfully move to the My Contest Activity
    fun updateContestMatchSuccess(){
        hideProgressDialog()
        val intent=Intent(this,MyContestActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun onAddUserFund(view:View?){
        if (view==binding.llJoinContestUserFund){
            val intent=Intent(this,AddFundActivity::class.java)
            startActivity(intent)
        }
    }

    fun joinedContestSavedSuccessfully(joinedContestId:String){
        Log.v("JOINED_CONTEST_ID",joinedContestId.toString())
        val contestId=matchContest!!.contestId
        val seatLeft=matchContest!!.contestSeatLeft
        addBetToTeam(contestId,seatLeft)
    }
}