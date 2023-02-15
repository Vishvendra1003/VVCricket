package com.torrex.vcricket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.torrex.vcricket.activities.MainActivity
import com.torrex.vcricket.activities.SignInOptionActivity
import com.torrex.vcricket.constants.GlobalConstant

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
           // TODO("splash screen")
            startActivity(Intent(this, SignInOptionActivity::class.java))
        },GlobalConstant().SPLASH_SCREEN_DELAY)
    }
}