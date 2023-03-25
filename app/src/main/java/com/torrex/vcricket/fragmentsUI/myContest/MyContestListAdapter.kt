package com.torrex.vcricket.fragmentsUI.myContest

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.torrex.vcricket.constants.GlobalFunctions
import com.torrex.vcricket.databinding.MyContestListLayoutBinding
import com.torrex.vcricket.models.contests.MyJoinedContest

class MyContestListAdapter(private val context: Context, private val myContestList:List<MyJoinedContest>):RecyclerView.Adapter<MyContestListAdapter.MyContestViewHolder>() {

    private var itemOnClickListener:OnItemClickListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyContestViewHolder {
        return MyContestViewHolder(MyContestListLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return myContestList.size
    }

    override fun onBindViewHolder(holder: MyContestViewHolder, position: Int) {
        val contest=myContestList[position]
        if (contest.team1img.isNotEmpty() && contest.team2img.isNotEmpty()){
            GlobalFunctions.loadUserPicture(context,contest.team1img,holder.binding.ivMyContestTeam1Image)
            GlobalFunctions.loadUserPicture(context,contest.team2img,holder.binding.ivMyContestTeam2Image)
        }
        if (contest.teamSelected==1){
            holder.binding.tvMyContestTeamSelected.text=contest.team1Name
        }
        else{
            holder.binding.tvMyContestTeamSelected.text=contest.team2Name
        }
        holder.binding.tvMyContestStatus.text=contest.matchStatus
        holder.binding.tvMyContestBetPrice.text=contest.betPrice.toString()
        holder.binding.tvMyContestBetStatus.text=contest.userStatus
        holder.binding.tvMyContestLeagueName.text=contest.matchName
        holder.binding.tvMyContestLeagueTime.text=contest.matchDate
        holder.binding.tvMyContestTeam1Name.text=contest.team1Name
        holder.binding.tvMyContestTeam2Name.text=contest.team2Name
        holder.binding.tvMyContestTeam1Score.text=contest.team1Score
        holder.binding.tvMyContestTeam2Score.text=contest.team2Score

        holder.itemView.setOnClickListener {
            itemOnClickListener?.onItemClicked(position,contest)
        }
    }


    class MyContestViewHolder(val binding:MyContestListLayoutBinding):ViewHolder(binding.root){

    }
    fun setOnClickListener(itemClickListener: OnItemClickListener){
        this.itemOnClickListener=itemClickListener
    }

    interface OnItemClickListener{
        fun onItemClicked(position:Int,contest:MyJoinedContest)
    }
}