package com.louis.bangkitbfaasubmission.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.louis.bangkitbfaasubmission.R
import com.louis.bangkitbfaasubmission.utils.ViewModelFactory
import com.louis.bangkitbfaasubmission.viewmodel.SplashViewModel
import kotlinx.coroutines.*

@SuppressLint("CustomSplashScreen")
class SplashscreenActivity : AppCompatActivity() {

    private val scope = CoroutineScope(Dispatchers.Main)

    private val splashViewModel: SplashViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        scope.launch {
            setThemeSettings()
            delay(timeDelay)
            Intent(this@SplashscreenActivity, MainActivity::class.java).also {
                startActivity(it)
            }
            finish()
        }
    }

    override fun onPause() {
        scope.cancel()
        super.onPause()
    }

    private fun setThemeSettings() {
        splashViewModel.getDarkThemeSettings().observe(this@SplashscreenActivity) { isActive ->
            if (isActive) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    companion object {
        const val timeDelay = 1500L
    }
}