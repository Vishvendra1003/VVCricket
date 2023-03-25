package com.torrex.vcricket.modules

import android.app.*
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.torrex.vcricket.R

open class BaseFragment():Fragment() {

    private lateinit var mProgressDialog:Dialog
    //notification channel
    val CHANNEL_ID="VCRICKET"
    val CHANNEL_NAME="VCRICKET CHANNEL"
    val CHANNEL_DESCRIPTION="VCRICKET NOTIFICATION"

    //Progress Bar
    fun showProgressDialog(text:String){

        val builder= AlertDialog.Builder(context)
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


    fun  sendNotification(context: Context,msg:String,pendingIntent: PendingIntent){
        registerNotificationChannel(context)
        val notifyBuilder= NotificationCompat.Builder(context,CHANNEL_ID)
            .setContentTitle("VCricket").setContentText(msg)
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        val notificationManager= NotificationManagerCompat.from(context)
        notificationManager.notify(1,notifyBuilder)
    }

    private fun registerNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val channel= NotificationChannel(CHANNEL_ID,CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT).apply {
                description=CHANNEL_DESCRIPTION
            }
            //register channel to System
            val notificationManager: NotificationManager =context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


}