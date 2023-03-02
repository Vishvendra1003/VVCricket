package com.torrex.vcricket.fragmentsUI.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.torrex.vcricket.constants.GlobalFunctions
import com.torrex.vcricket.models.currentMatch.CurrentMatchModel
import com.torrex.vcricket.modules.BaseActivity
import com.torrex.vcricket.retrofitData.RetrofitHelper
import com.torrex.vcricket.retrofitData.VCricketApi
import com.torrex.vcricket.sharedpreference.MatchSharedPreference
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val context: Context) : ViewModel() {

    private var _currentMatchList= MutableLiveData<CurrentMatchModel?>()
    val currentMatchModel: LiveData<CurrentMatchModel?> =_currentMatchList

    init {
        //getCurrentMatchListFromAPI()
    }

    fun getCurrentMatchListFromAPI() {
        //BaseActivity().showProgressDialog("Wait")

        if (GlobalFunctions.checkNetworkConnection(context)) {
            val vCricketApiInstance=RetrofitHelper.getVCricketInstance().create(VCricketApi::class.java)
            //for API Data transfer viewModelScope is in mvvm
            viewModelScope.launch {
                vCricketApiInstance.getUpComingMatches().enqueue(object:
                    Callback<CurrentMatchModel>{

                    override fun onResponse(call: Call<CurrentMatchModel>, response: Response<CurrentMatchModel>) {
                        if (response?.body()!=null){
                            Log.v("JSON_RESPONSE_MATCH",response.body().toString())
                            val response=response.body()
                            _currentMatchList.value=response
                            MatchSharedPreference(context).saveCurrentMatchListOffLine(response!!)
                        }
                    }

                    override fun onFailure(call: Call<CurrentMatchModel>, t: Throwable) {
                        Log.v("JSON_API_ERROR",t.message.toString())
                    }
                })
            }
        }

        else{
            _currentMatchList.value=MatchSharedPreference(context).getCurrentMatchListOffLine()
        }
        //BaseActivity().hideProgressDialog()
    }
}