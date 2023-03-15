package com.torrex.vcricket.activities.matches

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.torrex.vcricket.constants.DataBaseConstant
import com.torrex.vcricket.constants.GlobalFunctions
import com.torrex.vcricket.databinding.MatchContestCardListBinding
import com.torrex.vcricket.firebase.FireBaseContest
import com.torrex.vcricket.models.contests.MatchContest
import com.torrex.vcricket.models.currentMatch.Data

class MatchContestRecycleViewAdapter(private val context: Context,private val matchList:ArrayList<MatchContest>):RecyclerView.Adapter<MatchContestRecycleViewAdapter.MatchContestHolder>(){

        private var onClickListener:SetOnClickListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchContestHolder {
        return MatchContestHolder(MatchContestCardListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return matchList.size
    }

    override fun onBindViewHolder(holder: MatchContestHolder, position: Int) {
        val contest=matchList[position]
        val contestSeatLeft=contest.contestSeatLeft
        val contestSeatFilled=contest.contestTotalSeat-contest.contestSeatLeft
        val seatResult="Seat:${contestSeatFilled}/${contest.contestTotalSeat}"
        holder.binding.tvContestHeading.text=contest.contestName
        holder.binding.tvTeam1NameMatchContest.text=contest.contestTeam1Name
        holder.binding.tvTeam2NameMatchContest.text=contest.contestTeam2Name
        holder.binding.btnBetPrizeTeam1.text=contest.contestBetPrice.toString()
        holder.binding.btnBetPrizeTeam2.text=contest.contestBetPrice.toString()
        holder.binding.tvBetTeam1Rate.text=contest.contestTeam1BetPrice.toString()
        holder.binding.tvBetTeam2Rate.text=contest.contestTeam2BetPrice.toString()
        holder.binding.tvSeatLeft.text=seatResult

        //Image load for team
        GlobalFunctions.loadUserPicture(context,contest.contestTeam1Img,holder.binding.ivTeam1MatchContest)
        GlobalFunctions.loadUserPicture(context,contest.contestTeam2Img,holder.binding.ivTeam2MatchContest)

        //Button setOn clickListener
        holder.binding.btnBetPrizeTeam1.setOnClickListener {
            if (contestSeatLeft>0){
                if (onClickListener!=null){
                    val teamSelected=1
                    onClickListener!!.onItemClicked(position,contest,teamSelected)
                }
            }
            else{
                Toast.makeText(context,"Contest Full",Toast.LENGTH_SHORT).show()
            }
        }

        holder.binding.btnBetPrizeTeam2.setOnClickListener {
            if (contestSeatLeft>0){
                if (onClickListener!=null){
                    val teamSelected=2
                    onClickListener!!.onItemClicked(position,contest,teamSelected)
                }
            }
            else{
                Toast.makeText(context,"Contest Full",Toast.LENGTH_SHORT).show()
            }
        }

        //SetUpProgressBar
        val progressBarMax=contest.contestTotalSeat
        val progressBar=holder.binding.betSeatProgressBar
        progressBar.max=progressBarMax
        progressBar.progress=contestSeatFilled

    }


    class MatchContestHolder(val binding:MatchContestCardListBinding):RecyclerView.ViewHolder(binding.root){

    }

    fun setOnClickListener(onClickListener: SetOnClickListener){
        this.onClickListener=onClickListener
    }
    interface SetOnClickListener{
        fun onItemClicked(position:Int,currentMatchModel:MatchContest,teamSelected:Int)
    }
}