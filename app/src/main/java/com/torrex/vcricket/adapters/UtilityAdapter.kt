package com.torrex.vcricket.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.torrex.vcricket.constants.GlobalFunctions

@BindingAdapter("imageFromUrl")
fun ImageView.imageFromUrl(url:String){
            GlobalFunctions.loadUserPicture(this.context,url,this)
        }
