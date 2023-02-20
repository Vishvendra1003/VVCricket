package com.torrex.vcricket.modules

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.torrex.vcricket.R
import kotlinx.android.synthetic.main.dialog_progress.*
import java.util.Calendar

open class BaseActivity: AppCompatActivity() {
    private var doubleBackToExitPressedOnce=false
    private lateinit var mProgressDialog: Dialog

    //Progress Bar
    fun showProgressDialog(text:String){
        mProgressDialog= Dialog(this)
        mProgressDialog.setContentView(R.layout.dialog_progress)
        mProgressDialog.tv_progress_bar.text=text
        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()
    }

    fun hideProgressDialog(){
        mProgressDialog.dismiss()
    }

    fun doubleBackToExit(){
        if (doubleBackToExitPressedOnce){
            //finish()
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
}