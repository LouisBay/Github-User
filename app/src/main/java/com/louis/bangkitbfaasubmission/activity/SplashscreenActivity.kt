package com.louis.bangkitbfaasubmission.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.louis.bangkitbfaasubmission.R

@SuppressLint("CustomSplashScreen")
class SplashscreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashscreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, timeDelay)
    }

    companion object {
        const val timeDelay = 2000L
    }
}