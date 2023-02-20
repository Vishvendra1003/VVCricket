package com.torrex.vcricket.activities.mainUi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.torrex.vcricket.R
import com.torrex.vcricket.constants.GlobalConstant
import com.torrex.vcricket.constants.GlobalValueConstant
import com.torrex.vcricket.firebase.FireBaseUserDataBase
import com.torrex.vcricket.models.User
import com.torrex.vcricket.modules.BaseActivity
import kotlinx.android.synthetic.main.activity_register_with_phone.*

class RegisterWithPhoneActivity : BaseActivity(), View.OnClickListener {

    var mUserId:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_with_phone)

        setUpActionBar()

        if (intent.hasExtra(GlobalConstant.USER_PHONE_DATA)){
            et_signUp_mobile_phone.setText(intent.getStringExtra(GlobalConstant.USER_PHONE_DATA))
            et_signUp_mobile_phone.isEnabled=false
        }
        if (intent.hasExtra(GlobalConstant.USER_ID_PHONE_SIGNIN)){
            mUserId=intent.getStringExtra(GlobalConstant.USER_ID_PHONE_SIGNIN)!!
        }
        btn_signUp_phone.setOnClickListener(this)
        ib_date_picker_phone.setOnClickListener(this)
        iv_profile_register_image_phone.setOnClickListener(this)

    }


    //setting up action Bar
    private fun setUpActionBar(){
        setSupportActionBar(toolbar_register_activity_phone)

        val actionBar=supportActionBar
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        }
        toolbar_register_activity_phone.setNavigationOnClickListener{onBackPressed()}
    }

    override fun onClick(v: View?) {
        if (v!=null){
            when(v.id){
                R.id.btn_signUp_phone->{
                    //Storing data in the Firebase store
                    registerUserWithPhone()
                }
                R.id.iv_profile_register_image_phone->{

                }
                R.id.ib_date_picker_phone->{
                    datePickerCustom(tv_signUp_dob_phone)
                }
            }
        }
    }

    private fun registerUserWithPhone() {
        if (validateUserDetails()){
            val mPhone=et_signUp_mobile_phone.text.toString().trim { it<=' ' }.toLong()
            var gender:String=""
            if (rb_male_phone.isChecked){
                gender=GlobalValueConstant.MALE
            }
            else{
                gender=GlobalValueConstant.FEMALE
            }
            showProgressDialog("Wait")

            val user=User(mUserId,
                et_signUp_first_name_phone.text.toString().trim{it <=' '},
                et_signUp_last_name_phone.text.toString().trim{it <=' '},
            "",mPhone,gender,tv_signUp_dob_phone.text.toString().trim { it<=' ' }
            )
            FireBaseUserDataBase().registerUser(this,user)

        }
    }


    //Validate the user details entered
    private fun validateUserDetails():Boolean{
        return when{
            TextUtils.isEmpty(et_signUp_first_name_phone.text.toString().trim { it<=' '})->{
                showErrorSnackBar("Please Enter First Name",true)
                false
            }
            TextUtils.isEmpty(et_signUp_last_name_phone.text.toString().trim { it<=' '})->{
                showErrorSnackBar("Please Enter Last Name",true)
                false
            }

            TextUtils.isEmpty(tv_signUp_dob_phone.text.toString().trim{it<=' '})-> {
                showErrorSnackBar("Please Enter DOB",true)
                false
            }

            TextUtils.isEmpty(et_signUp_mobile_phone.text.toString().trim { it<=' '})->{
                showErrorSnackBar("Please Enter Correct MobileNo",true)
                false
            }
            et_signUp_mobile_phone.text.toString().trim { it<=' '}.length!=10->{
                showErrorSnackBar("Please Enter Correct MobileNo",true)
                false
            }
            else-> {
                true
            }
        }

    }



    fun userRegisteredSuccessWithPhone(user:User){
        hideProgressDialog()
        Log.v("Success","Success")
        Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()
        finish()
        val intent=Intent(this,MainActivity::class.java)
        intent.putExtra(GlobalConstant.USER_MODEL_DATA,user)
        startActivity(intent)
    }

}