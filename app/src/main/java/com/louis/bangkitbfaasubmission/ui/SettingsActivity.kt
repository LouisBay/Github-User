package com.louis.bangkitbfaasubmission.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.louis.bangkitbfaasubmission.databinding.ActivitySettingsBinding
import com.louis.bangkitbfaasubmission.utils.ViewModelFactory
import com.louis.bangkitbfaasubmission.viewmodel.SettingsViewModel

class SettingsActivity : AppCompatActivity() {

    private lateinit var activitySettingsBinding: ActivitySettingsBinding

    private val settingsViewModel: SettingsViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySettingsBinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(activitySettingsBinding.root)

        setToolbar()
        setTheme()
    }

    private fun setTheme() {
        settingsViewModel.apply {

            getDarkThemeSettings().observe(this@SettingsActivity) { isActive ->
                if (isActive) {
                    activitySettingsBinding.switchTheme.isChecked = true
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    activitySettingsBinding.switchTheme.isChecked = false
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }

            activitySettingsBinding.apply {
                switchTheme.setOnCheckedChangeListener { _, isChecked -> saveDarkThemeSettings(isChecked) }

                themeSettingLayout.setOnClickListener {
                    if (switchTheme.isChecked) {
                        switchTheme.isChecked = false
                        saveDarkThemeSettings(false)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    } else {
                        switchTheme.isChecked = true
                        saveDarkThemeSettings(true)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                }
            }


        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setToolbar() {
        setSupportActionBar(activitySettingsBinding.toolbarSettings)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }
}