package com.torrex.vcricket.fragmentsUI.payment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.torrex.vcricket.R
import com.torrex.vcricket.constants.DataBaseConstant
import com.torrex.vcricket.databinding.PaymentHistoryListBinding
import com.torrex.vcricket.models.payment.TransactionData
import java.text.SimpleDateFormat

class PaymentHistoryAdapter(private val context: Context,private val paymentList: List<TransactionData>):RecyclerView.Adapter<PaymentHistoryAdapter.PaymentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):PaymentViewHolder {
        return PaymentViewHolder(PaymentHistoryListBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder:PaymentViewHolder, position: Int) {
        val transactionData=paymentList[position]
        val dateFormat=SimpleDateFormat("MM/dd/yyyy")
        val date=dateFormat.format(transactionData.date)
        holder.binding.tvPaymentDate.text=date
        holder.binding.tvPaymentAmount.text=transactionData.amount.toString()
        holder.binding.tvPaymentStatus.text=transactionData.transactionStatus
        holder.binding.tvDateGroupHeading.text=transactionData.transactionId
        holder.binding.tvPaymentTypeTitle.text=transactionData.transactionType

        if (transactionData.transactionType==DataBaseConstant.DEPOSIT){
            holder.binding.tvPaymentTypeTitle.setTextColor(ContextCompat.getColor(context,R.color.green))
            holder.binding.ivPaymentTypeImage.setImageResource(R.drawable.ic_baseline_notifications_active_24)
        }
        else{
            holder.binding.tvPaymentTypeTitle.setTextColor(ContextCompat.getColor(context,R.color.red))
        }
        if (transactionData.transactionStatus==DataBaseConstant.SUCCESS){
            holder.binding.tvPaymentStatus.setTextColor(ContextCompat.getColor(context,R.color.green))
        }
        else{
            holder.binding.tvPaymentStatus.setTextColor(ContextCompat.getColor(context,R.color.red))
        }


    }

    override fun getItemCount(): Int {
        return paymentList.size
    }

    class PaymentViewHolder(val binding:PaymentHistoryListBinding):RecyclerView.ViewHolder(binding.root){
    }
}