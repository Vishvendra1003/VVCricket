package com.torrex.vcricket.constants

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.provider.MediaStore.Images.Media
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object GlobalFunctions {

    fun showImageChooser(activity: Activity){
        val galleryIntent=Intent(Intent.ACTION_PICK,Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(galleryIntent,GlobalConstant.PICK_IMAGE_REQUEST_CODE)
    }

    //Function to load Picture
    fun loadUserPicture(context: Context,image:Any,imageView:ImageView){
        try {
            Glide.with(context).load(Uri.parse(image.toString()))
                .centerCrop().into(imageView)
        }
        catch (e:IOException){
            e.printStackTrace()
        }
    }

    //Get File Extension Function
    fun getFileExtension(activity: Activity,uri:Uri?):String?{
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }

    //Function to check Network
    fun checkNetworkConnection(context: Context):Boolean{
        if (context==null) return false
        val connectivityManager=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q){
            val capabilities=connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities!=null){
                when{
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)->{
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)->{
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)->{
                        return true
                    }
                }
            }
        }
        else{
            val activeNetworkInfo=connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    fun getDateFromGMTString(date: String):String {
        val inputFormat= SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        inputFormat.timeZone= TimeZone.getTimeZone("GMT")
        val dateGMT=inputFormat.parse(date)

        val outputDate= SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val date=outputDate.format(dateGMT)
        Log.v("DATE",date.toString())
        return date.toString()

    }


    //Function to check Location Permission
    fun checkLocationPermission(context: Context):Boolean{
        return if (ActivityCompat.checkSelfPermission(context,android.Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context,android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION),GlobalConstant.LOCATION_PERMISSION)
            false
        }
        else{
            true
        }
    }



}