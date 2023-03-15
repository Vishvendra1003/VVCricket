package com.torrex.vcricket.fragmentsUI.gallery

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.torrex.vcricket.constants.GlobalFunctions
import kotlin.math.log

class GalleryViewModel(private val context: Context) : ViewModel() {

    var gpsLocation=MutableLiveData<String>()
    private var currentLocation:Location?=null
    lateinit var locationManager: LocationManager

    fun checkGPSLocation() {
        if(GlobalFunctions.checkLocationPermission(context)){
            locationManager=context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val hasGPS=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val hasNetwork=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            val gpsLocationListener= LocationListener { p0 ->
                gpsLocation.value=p0.latitude.toString()+p0.longitude.toString()
                Log.v("LOCATION", p0.latitude.toString())
            }
            if (hasGPS){
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,3000,0F,gpsLocationListener)
            }

        }
    }
}