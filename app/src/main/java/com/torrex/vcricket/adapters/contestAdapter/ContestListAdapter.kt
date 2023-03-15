package com.torrex.vcricket.adapters.contestAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.torrex.vcricket.R
import com.torrex.vcricket.databinding.ContestPriceItemListBinding
import com.torrex.vcricket.models.contests.MatchContest

class ContestListAdapter(private val context: Context,private val contestList:List<MatchContest>):
    ArrayAdapter<MatchContest?>(context, R.layout.contest_price_item_list,contestList) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //return super.getView(position, convertView, parent)

        var convertView=convertView
        convertView=LayoutInflater.from(context).inflate(R.layout.contest_price_item_list,null,true)

        val contestPrice=convertView.findViewById(R.id.tv_contest_price_item_price) as TextView
        val contestSeat=convertView.findViewById(R.id.tv_contest_seat) as TextView
        val contestDelete=convertView.findViewById(R.id.iv_contest_delete) as ImageView

        contestPrice.text=contestList[position].contestBetPrice.toString()
        contestSeat.text=contestList[position].contestTotalSeat.toString()

        return convertView
    }

}