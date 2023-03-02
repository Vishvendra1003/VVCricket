package com.torrex.vcricket.constants

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.provider.MediaStore.Images.Media
import android.webkit.MimeTypeMap
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.IOException

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
}