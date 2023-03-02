package com.torrex.vcricket.fragmentsUI.payment

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.torrex.vcricket.firebase.FireBasePaymentData
import com.torrex.vcricket.firebase.FireBaseUserDataBase
import com.torrex.vcricket.models.payment.UserFund
import com.torrex.vcricket.sharedpreference.UserSharedPreference
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PaymentViewModel(private val context: Context): ViewModel() {

    var userBalance=MutableLiveData<String?>("0")

    fun setUserAmount(userFund:UserFund){
        userBalance.value=userFund.userFund.toString()
    }
}