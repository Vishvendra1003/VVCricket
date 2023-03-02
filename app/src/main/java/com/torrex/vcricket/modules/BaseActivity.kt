package com.torrex.vcricket.modules

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.ProgressDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.google.android.material.snackbar.Snackbar
import com.torrex.vcricket.R
import java.util.Calendar

open class BaseActivity: AppCompatActivity() {
    private var doubleBackToExitPressedOnce=false
    private lateinit var mProgressDialog: Dialog

    //notification channel
    val CHANNEL_ID="VCRICKET"
    val CHANNEL_NAME="VCRICKET CHANNEL"
    val CHANNEL_DESCRIPTION="VCRICKET NOTIFICATION"


    //Progress Bar
    fun showProgressDialog(text:String){

        val builder=AlertDialog.Builder(this)
        builder.setView(R.layout.dialog_progress)
        builder.setMessage(text)
        mProgressDialog=builder.create()
        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()
    }

    fun hideProgressDialog(){
        mProgressDialog.dismiss()
    }

    fun doubleBackToExit(){
        if (doubleBackToExitPressedOnce){
            finish()
            super.getOnBackPressedDispatcher()
            return
        }
        this.doubleBackToExitPressedOnce=true
        Toast.makeText(this,resources.getString(R.string.please_click_back_again_to_exit), Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackToExitPressedOnce=false },2000)

    }

    //Function for error Snackbar
    fun showErrorSnackBar(message:String,errorMessage:Boolean){
        val snackBar=Snackbar.make(findViewById(android.R.id.content),message,Snackbar.LENGTH_LONG)
        val snackBarView=snackBar.view

        if (errorMessage){
            snackBarView.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.red))
        }
        else{
            snackBarView.setBackgroundColor(ContextCompat.getColor(applicationContext,R.color.green))
        }
        snackBar.show()
    }

    //Date Picker
    fun datePickerCustom(datePicked: TextView){
        val calender=Calendar.getInstance()
        val year=calender.get(Calendar.YEAR)
        val month=calender.get(Calendar.MONTH)
        val day=calender.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog=DatePickerDialog(this,{view,year,month,day->
            datePicked.text="$day/$month/$year"
        },year,month,day)
        datePickerDialog.show()
    }



    //Notification-------------------------------

    fun sendNotification(context: Context,msg:String,pendingIntent: PendingIntent){
        registerNotificationChannel()
        val notifyBuilder=NotificationCompat.Builder(context,CHANNEL_ID)
            .setContentTitle("VCricket").setContentText(msg)
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        val notificationManager=NotificationManagerCompat.from(this)
        notificationManager.notify(1,notifyBuilder)
    }

    private fun registerNotificationChannel() {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val channel=NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT).apply {
                description=CHANNEL_DESCRIPTION
            }
            //register channel to System
            val notificationManager:NotificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}