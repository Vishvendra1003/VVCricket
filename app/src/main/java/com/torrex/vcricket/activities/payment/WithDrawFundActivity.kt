package com.torrex.vcricket.activities.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.torrex.vcricket.R
import com.torrex.vcricket.databinding.ActivityWithDrawFundBinding

class WithDrawFundActivity : AppCompatActivity() {

    private lateinit var binding:ActivityWithDrawFundBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=DataBindingUtil.setContentView(this,R.layout.activity_with_draw_fund)
        binding.btnAddAmountWithdrawFund.setOnClickListener {
            Toast.makeText(this, "Wiil add withdraw soon!!!!", Toast.LENGTH_SHORT).show()
            //TODO("Withdraw functionality")
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}