package com.torrex.vcricket.firebase

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject
import com.torrex.vcricket.activities.payment.AddFundActivity
import com.torrex.vcricket.constants.DataBaseConstant
import com.torrex.vcricket.constants.GlobalConstant
import com.torrex.vcricket.databinding.FragmentPaymentBinding
import com.torrex.vcricket.firebase.FirebaseStoreClass.mFireStore
import com.torrex.vcricket.fragmentsUI.payment.PaymentFragment
import com.torrex.vcricket.models.payment.TransactionData
import com.torrex.vcricket.models.payment.UserFund

class FireBasePaymentData {

    //Get User Fund
    fun getUserFund(fragment: Fragment,userId:String){
        mFireStore.collection(DataBaseConstant.USER_FUND)
            .document(userId)
            .get()
            .addOnSuccessListener {
                Log.v("USER_FUND",it.data.toString())
                val userFund=it.toObject(UserFund::class.java)
                when(fragment){
                    is PaymentFragment->{
                        fragment.getUserFundSuccess(userFund!!)
                    }
                }
            }
            .addOnFailureListener{e->
                Log.v("USER_FUND_ERROR",e.message!!)
            }
    }

    //Get User Fund
    fun getUserFund(activity:Activity,userId:String){
        mFireStore.collection(DataBaseConstant.USER_FUND)
            .document(userId)
            .get()
            .addOnSuccessListener {
                Log.v("USER_FUND",it.data.toString())
                val userFund=it.toObject(UserFund::class.java)
                when(activity){
                    is AddFundActivity->{
                        activity.getUserFundSuccess(userFund!!)
                    }
                }
            }
            .addOnFailureListener{e->
                Log.v("USER_FUND_ERROR",e.message!!)
            }
    }
    //Update UserFund---------------
    fun updateUserFund(activity: Activity,totalUserFund:Double,userId:String){
        mFireStore.collection(DataBaseConstant.USER_FUND)
            .document(userId)
            .update(DataBaseConstant.USERFUND,totalUserFund)
            .addOnSuccessListener {document->
                when(activity){
                    is AddFundActivity->{
                        activity.fundUpdatedSuccessfully(totalUserFund)
                    }
                }
            }
    }
    //Adding Transaction Details in Transaction Database
    fun storeTransactionDetails(activity: AddFundActivity,transactionData: TransactionData){
        mFireStore.collection(DataBaseConstant.TRANSACTION_DATA)
            .add(transactionData)
            .addOnSuccessListener {document->
                when(activity){
                    is AddFundActivity->{
                        activity.transactionDetailsAddedSuccessfully(transactionData)
                    }
                }
            }
    }



    //Add Transaction Data to User Account
    fun saveTransactionDetailsToUserAccount(activity: AddFundActivity,transactionData: TransactionData){
        mFireStore.collection(DataBaseConstant.USER_TRANSACTION_DATA)
            .add(transactionData)
            .addOnSuccessListener {
                when(activity){
                    is AddFundActivity->{
                        activity.transactionDetailsAddedSuccessfullyToUser()
                    }
                }
            }
    }

    //Get Transaction Data for the User
    fun getTransactionDataForUser(fragment: Fragment,userId: String){
        mFireStore.collection(DataBaseConstant.USER_TRANSACTION_DATA)
            .whereEqualTo(DataBaseConstant.USERID,userId)
            .get()
            .addOnSuccessListener { document->
                val paymentList:ArrayList<TransactionData> =ArrayList()
                Log.v("TRANSACTION_DATA_USER",document.toString())

                for(i in document.documents){
                    val transactionData=i.toObject(TransactionData::class.java)
                    paymentList.add(transactionData!!)
                }
                when(fragment){
                    is PaymentFragment->{
                        fragment.getPaymentTransactionListSuccess(paymentList)
                    }
                }


            }
    }
}