package com.torrex.vcricket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.torrex.vcricket.activities.mainUi.LoginActivity
import com.torrex.vcricket.activities.mainUi.MainActivity
import com.torrex.vcricket.activities.mainUi.SignInOptionActivity
import com.torrex.vcricket.constants.GlobalConstant

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
           // TODO("splash screen")
            finish()

            if(FirebaseAuth.getInstance().currentUser!=null){
                Log.v(this.javaClass.simpleName,"")
                startActivity(Intent(this,MainActivity::class.java))
            }
            else{
                startActivity(Intent(this, LoginActivity::class.java))
            }
        },GlobalConstant.SPLASH_SCREEN_DELAY)
    }
}