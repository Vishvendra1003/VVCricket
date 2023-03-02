package com.torrex.vcricket.models.payment


data class TransactionData(
    val upiId:String="",
    val userId:String="",
    val date:Long=0,
    val payeeName:String="",
    val transactionId:String="",
    val transactionRefId:String="",
    val amount:Double=0.00,
    val payeeMerchantCode:String="",
    val despription:String="",
    val transactionType:String=""

)
