package com.torrex.vcricket.activities.profileUi

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.torrex.vcricket.R
import com.torrex.vcricket.constants.GlobalConstant
import com.torrex.vcricket.constants.GlobalValueConstant
import com.torrex.vcricket.databinding.ActivityProfileBinding
import com.torrex.vcricket.models.User
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var profileViewModel:ProfileViewModel
    private lateinit var mUser:User
    val genderSpinner= arrayOf("Male","Female")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_profile)

        if (intent.hasExtra(GlobalConstant.USER_MODEL_DATA)){
            mUser=intent.getParcelableExtra(GlobalConstant.USER_MODEL_DATA)!!
            profileViewModel= ViewModelProvider(this,ProfileViewModelFactory(this,mUser))[ProfileViewModel::class.java]
        }

        //Button Functionality for Edit
        btn_profile_edit.setOnClickListener {
            if (btn_profile_edit.text.toString().trim { it<= ' ' }=="Edit"){
                et_profile_email.isEnabled=true
                et_profile_first_name.isEnabled=true
                et_profile_last_name.isEnabled=true
                et_profile_mobile.isEnabled=false

            }
        }
    }
}