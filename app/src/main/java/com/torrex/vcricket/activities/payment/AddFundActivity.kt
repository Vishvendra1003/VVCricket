package com.torrex.vcricket.activities.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.withStarted
import com.torrex.vcricket.R
import com.torrex.vcricket.constants.GlobalConstant
import com.torrex.vcricket.constants.GlobalFunctions
import com.torrex.vcricket.databinding.ActivityAddFundBinding
import com.torrex.vcricket.firebase.FireBasePaymentData
import com.torrex.vcricket.models.payment.TransactionData
import com.torrex.vcricket.models.payment.UserFund
import com.torrex.vcricket.modules.BaseActivity
import com.torrex.vcricket.sharedpreference.UserSharedPreference
import dev.shreyaspatil.easyupipayment.EasyUpiPayment
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener
import dev.shreyaspatil.easyupipayment.model.TransactionDetails
import dev.shreyaspatil.easyupipayment.model.TransactionStatus
import java.text.DecimalFormat
import java.util.Date

class AddFundActivity : BaseActivity(),PaymentStatusListener{

    private var amount:Double=0.00
    private val UPIId="8840344272@paytm"
    private val UPIName="Vishvendra"
    private val UPIDescription="Payment to 8840344272"
    private var UPITransactionID=""
    private var UPITransactionRefId=""
    private val UPIMerchantCode="1003"
    private var date:Long=0
    private val transactionType="AddFund"
    private var userId=""

    private var initialFund=0.0


    private lateinit var binding:ActivityAddFundBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_add_fund)

        userId=UserSharedPreference(this).getUserSharedPref().id

        FireBasePaymentData().getUserFund(this,userId)

        binding.btnAddAmountAddFund.setOnClickListener {
            val tAmount=binding.etAddFundEnterAmount.text.toString().trim { it<= ' ' }
            amount=tAmount.toDouble()+0.00
            startUPIPayment(amount.toString())
        }
    }

    private fun startUPIPayment(amount:String){

        showProgressDialog("Adding Fund Please wait!!!")
        //Setting Transaction ID as current Date
        date=System.currentTimeMillis()
        UPITransactionID=date.toString()
        UPITransactionRefId=UPITransactionID

        //Add Fund
        val easyUpiPayment=EasyUpiPayment(this){
            this.payeeVpa=UPIId
            this.payeeName=UPIName
            this.transactionId=UPITransactionID
            this.transactionRefId=UPITransactionRefId
            this.amount=amount
            this.payeeMerchantCode=UPIMerchantCode
            this.description=UPIDescription
        }
        easyUpiPayment.startPayment()
        easyUpiPayment.setPaymentStatusListener(this)
    }

    override fun onTransactionCancelled() {
        Toast.makeText(this,"Cancelled by user", Toast.LENGTH_SHORT).show()
        TODO("Not yet implemented")
        hideProgressDialog()
    }

    override fun onTransactionCompleted(transactionDetails: TransactionDetails) {
        Log.v("TransactionDetails", transactionDetails.toString())
        hideProgressDialog()
        if (transactionDetails.transactionStatus== TransactionStatus.FAILURE){
            showErrorSnackBar("Payment Failed",true)
            var _addedFund=transactionDetails.amount!!.toDouble()
            val decFormat=DecimalFormat("#.##")
            val addedFund=decFormat.format(_addedFund).toDouble()
            calculateFundAmount(addedFund)

        }
        if (transactionDetails.transactionStatus==TransactionStatus.SUCCESS){
            var _addedFund=transactionDetails.amount!!.toDouble()
            val decFormat=DecimalFormat("#.##")
            val addedFund=decFormat.format(_addedFund).toDouble()
            calculateFundAmount(addedFund)
        }
    }


    fun transactionDetailsAddedSuccessfully(transactionData: TransactionData){
        //add to transaction Detail to user Account Firebase
        FireBasePaymentData().saveTransactionDetailsToUserAccount(this,transactionData)
    }

    fun transactionDetailsAddedSuccessfullyToUser(){
        hideProgressDialog()
        showErrorSnackBar("Amount Added Successfully",false)
        finish()
    }

    private fun calculateFundAmount(addedFund:Double) {
        val totalFund=addedFund+initialFund
        FireBasePaymentData().updateUserFund(this,totalFund,userId)
    }

    fun fundUpdatedSuccessfully(totalFund:Double){
        val transactionData=TransactionData(
            UPIId,
            userId,
            date,
            UPIName,
            UPITransactionID,
            UPITransactionRefId,
            amount,
            UPIMerchantCode,
            UPIDescription,
            transactionType )
        FireBasePaymentData().storeTransactionDetails(this,transactionData)
    }

    fun getUserFundSuccess(userFund: UserFund){
        val decFormat=DecimalFormat("#.##")
        initialFund=decFormat.format(userFund.userFund).toDouble()
    }




}