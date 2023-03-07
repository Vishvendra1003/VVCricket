package com.torrex.vcricket.activities.adminAccount.adminContest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.torrex.vcricket.R
import com.torrex.vcricket.databinding.ActivityAddContestBinding
import com.torrex.vcricket.firebase.FireBaseContest
import com.torrex.vcricket.models.contests.MatchContest
import com.torrex.vcricket.models.currentMatch.Data
import com.torrex.vcricket.modules.BaseActivity
import com.torrex.vcricket.sharedpreference.MatchSharedPreference

class AddContestActivity : BaseActivity() {

    private lateinit var binding:ActivityAddContestBinding
    private lateinit var addContestViewModel:AddContestViewModel
    var betPriceList=ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_add_contest)
        addContestViewModel= ViewModelProvider(this,AddContestViewModelFactory(this))[AddContestViewModel::class.java]
        binding.contestMatchViewModel=addContestViewModel
        binding.lifecycleOwner=this

        addContestViewModel.getCurrentMatchDetails()

        showProgressDialog("Loading Contest")
        binding.submitContestListPrice.setOnClickListener {
            //Toast.makeText(this,"Submit clicked",Toast.LENGTH_SHORT).show()
            showProgressDialog("Saving Contest")
            val betPrice=binding.etBetPrice.text.toString().trim { it<= ' ' }.toInt()
            val contestSeat=binding.etContestSeat.text.toString().trim { it<=' ' }.toInt()
            addContestViewModel.submitContest(betPrice,contestSeat)
        }

      /*  binding.btnAddBetPriceToList.setOnClickListener{
            val betPrice=binding.etBetPrice.text.toString().trim { it<= ' ' }
            val contestSeat=binding.etContestSeat.text.toString().trim { it<=' ' }
            betPriceList.add(betPrice)
            addContestViewModel.betPriceList.value=betPriceList
        }*/

        addContestViewModel.contestList.observe(this, Observer {
            for(i in it){
                betPriceList.add(i.contestBetPrice.toString())
            }
            addContestViewModel.betPriceList.value=betPriceList
        })

        addContestViewModel.betPriceList.observe(this, Observer{
            setUpGridView()
        })


    }

    private fun setUpGridView() {
        val betPriceListView=binding.lvBetPriceList
        val gridAdapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,betPriceList)
        betPriceListView.adapter=gridAdapter
    }

    //contest Saved Successfully
    fun contestSavedSuccessfully(){
        hideProgressDialog()
        finish()
    }

    //Get Contest List
    fun getContestListSuccess(contestList:ArrayList<MatchContest>){
        addContestViewModel.contestList.value=contestList
        hideProgressDialog()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onResume() {
        super.onResume()
        //setUpGridView()
    }
}