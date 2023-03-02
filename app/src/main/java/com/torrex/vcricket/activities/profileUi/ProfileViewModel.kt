package com.torrex.vcricket.activities.profileUi

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.torrex.vcricket.R
import com.torrex.vcricket.constants.DataBaseConstant
import com.torrex.vcricket.constants.GlobalFunctions
import com.torrex.vcricket.firebase.FireBaseUserDataBase
import com.torrex.vcricket.models.User

class ProfileViewModel(val context: Context,private val user: User): ViewModel() {
    private val mUser=MutableLiveData<User>()
    val mUserLiveData:LiveData<User> get() =mUser
    val mUpdatedUser=HashMap<String,Any>()

    //User Details---
    val email=MutableLiveData<String>(user.email)
    val firstName= MutableLiveData<String>(user.firstName)
    val lastName=MutableLiveData<String>(user.lastName)
    val userGender=MutableLiveData<String>(user.gender)
    val userProfileImage=MutableLiveData<String>(user.image)


    init {
        mUser.value=user
    }



    fun saveContact(){

    }
    fun updateUserDetails(){
        /*
        //Setting values for User
        mUser.value=User(mUser.value!!.id,
        firstName.value!!,lastName.value!!,email.value!!,mUser.value!!.mobile,userGender.value!!,mUser.value!!.dob,userProfileImage.value!!,m)*/


        //Setting values in HashMap to Store
        mUpdatedUser[DataBaseConstant.EMAIL]=email.value!!
        mUpdatedUser[DataBaseConstant.FIRSTNAME]=firstName.value!!
        mUpdatedUser[DataBaseConstant.LASTNAME]=lastName.value!!
        mUpdatedUser[DataBaseConstant.GENDER]=userGender.value!!
        mUpdatedUser[DataBaseConstant.IMAGE]=userProfileImage.value!!
        mUpdatedUser[DataBaseConstant.PROFILECOMPLETE]=1
        mUpdatedUser[DataBaseConstant.USERACCOUNT]=true

    }




}