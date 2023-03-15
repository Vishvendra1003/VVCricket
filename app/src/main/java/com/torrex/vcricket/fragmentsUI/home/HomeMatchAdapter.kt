package com.torrex.vcricket.fragmentsUI.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.torrex.vcricket.constants.GlobalConstant
import com.torrex.vcricket.constants.GlobalFunctions
import com.torrex.vcricket.databinding.MatchCardViewBinding
import com.torrex.vcricket.models.currentMatch.CurrentMatchModel
import com.torrex.vcricket.models.currentMatch.Data
import com.torrex.vcricket.models.currentMatch.TeamInfo
import com.torrex.vcricket.sharedpreference.SharedPreferenceConstant.currentMatchModel
import java.text.SimpleDateFormat
import java.util.*

class HomeMatchAdapter(private val context: Context,private val matchList:CurrentMatchModel): RecyclerView.Adapter<HomeMatchAdapter.MatchViewHolder>() {

    private var onClickListener:ItemClickListener?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MatchViewHolder {
        return MatchViewHolder(MatchCardViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder:MatchViewHolder, position: Int) {
        val currentMatchModel=matchList.data[position]
        Log.v("CURRENT_TEAM",currentMatchModel.toString())

        if (currentMatchModel.teamInfo!=null){
            if (currentMatchModel.teamInfo.size>1){
                holder.binding.tvMatchHomeLeagueName.text=currentMatchModel.name
                //val date=getDateFromGMTString(currentMatchModel.dateTimeGMT)
                val date =GlobalFunctions.getDateFromGMTString(currentMatchModel.dateTimeGMT)
                holder.binding.tvMatchHomeLeagueTime.text=date

                holder.binding.tvMatchHomeTeam1Name.text=currentMatchModel.teamInfo[0].name
                holder.binding.tvMatchHomeTeam1Code.text=currentMatchModel.teamInfo[0].shortname
                holder.binding.tvMatchHomeTeam2Name.text=currentMatchModel.teamInfo[1].name
                holder.binding.tvMatchHomeTeam2Code.text=currentMatchModel.teamInfo[1].shortname

                    val teamImg1=currentMatchModel.teamInfo[0]?.img
                    val teamImg2=currentMatchModel.teamInfo[1].img

                    if (teamImg1!=null && teamImg2!=null){
                        GlobalFunctions.loadUserPicture(context,teamImg1,holder.binding.ivMatchHomeTeam1Image)
                        GlobalFunctions.loadUserPicture(context,teamImg2,holder.binding.ivMatchHomeTeam2Image)
                    }

            }

        }

        //on click Listener------
        holder.itemView.setOnClickListener {
            if (onClickListener!=null){
                onClickListener!!.onItemClicked(position,currentMatchModel)
            }
        }

    }



    override fun getItemCount(): Int {
        return matchList.data.size
    }

    fun setOnClickListener(onItemClickListener: ItemClickListener){
        this.onClickListener=onItemClickListener
    }
    interface ItemClickListener{
        fun onItemClicked(position:Int,currentMatchModel:Data)
    }

    class MatchViewHolder(val binding:MatchCardViewBinding): RecyclerView.ViewHolder(binding.root)


}