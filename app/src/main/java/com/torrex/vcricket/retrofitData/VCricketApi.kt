package com.torrex.vcricket.retrofitData

import com.google.gson.JsonElement
import com.torrex.vcricket.models.currentMatch.CurrentMatchModel
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.GET

interface VCricketApi {

    //Url: https://api.cricapi.com/v1/currentMatches?apikey=48d804a4-493d-456a-81be-833550a536fc&offset=0
    @GET("currentMatches?apikey=48d804a4-493d-456a-81be-833550a536fc&offset=0")
    fun getCurrentMatchDetails(): Call<CurrentMatchModel>

    @GET("matches?apikey=48d804a4-493d-456a-81be-833550a536fc&offset=0")
    fun getUpComingMatches(): Call<CurrentMatchModel>
}