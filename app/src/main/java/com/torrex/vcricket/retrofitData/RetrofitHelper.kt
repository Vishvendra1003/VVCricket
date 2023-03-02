package com.torrex.vcricket.retrofitData

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    val  vCricketBaseUrl="https://api.cricapi.com/v1/"

    fun getVCricketInstance():Retrofit{
        val retrofitVCricketInstance=Retrofit.Builder().baseUrl(vCricketBaseUrl)
            .addConverterFactory(GsonConverterFactory.create()).build()
        return retrofitVCricketInstance
    }
}