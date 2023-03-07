package com.torrex.vcricket.adapters.contestAdapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.torrex.vcricket.R
import com.torrex.vcricket.models.contests.MatchContest

class ContestListAdapter(context: Context,contestList:List<MatchContest>):
    ArrayAdapter<MatchContest?>(context, R.layout.contest_price_item_list,contestList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return super.getView(position, convertView, parent)
        
    }

}