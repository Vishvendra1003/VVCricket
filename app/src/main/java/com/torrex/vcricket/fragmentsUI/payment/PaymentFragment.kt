package com.torrex.vcricket.fragmentsUI.payment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.torrex.vcricket.R
import com.torrex.vcricket.activities.payment.AddFundActivity
import com.torrex.vcricket.activities.payment.WithDrawFundActivity
import com.torrex.vcricket.databinding.FragmentPaymentBinding
import com.torrex.vcricket.firebase.FireBasePaymentData
import com.torrex.vcricket.models.payment.UserFund
import com.torrex.vcricket.modules.BaseFragment
import com.torrex.vcricket.sharedpreference.UserSharedPreference

class PaymentFragment : BaseFragment() {
    private lateinit var binding:FragmentPaymentBinding
    private lateinit var paymentViewModel:PaymentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding=FragmentPaymentBinding.inflate(inflater,container,false)
        val root: View = binding.root
        paymentViewModel= ViewModelProvider(this,PaymentViewModelFactory(requireActivity()))[PaymentViewModel::class.java]
        binding.paymentViewModel=paymentViewModel
        binding.lifecycleOwner=this

        //Button OnClick Function
        binding.btnAddFund.setOnClickListener {
            addFund()
        }
        binding.btnWithdrawFund.setOnClickListener {
            withDraw()
        }


        return root
    }

    private fun withDraw() {
        val intent=Intent(requireActivity(),WithDrawFundActivity::class.java)
    }


    fun addFund(){
        val intent=Intent(requireActivity(), AddFundActivity::class.java)
        startActivity(intent)

    }

    private fun getUserAmount(){
        val userId=UserSharedPreference(requireActivity()).getUserSharedPref().id
        val userFund=FireBasePaymentData().getUserFund(this,userId)
        Log.v("USER_FUND",userFund.toString())
    }
    fun getUserFundSuccess(userFund:UserFund){
        hideProgressDialog()
        paymentViewModel.setUserAmount(userFund)
    }

    override fun onResume() {
        super.onResume()
        showProgressDialog("Loading...")
        getUserAmount()
    }





}