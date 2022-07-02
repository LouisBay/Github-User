package com.louis.bangkitbfaasubmission.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.louis.bangkitbfaasubmission.databinding.ActivityUserDetailBinding
import com.louis.bangkitbfaasubmission.loadCircleImage
import com.louis.bangkitbfaasubmission.model.User
import java.lang.StringBuilder

class   UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail User"

        getAndParseIntentData()
    }

    private fun getAndParseIntentData() {
        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User

        with(binding){
            ivDetailAvatar.loadCircleImage(user.avatar)
            tvDetailName.text = user.name
            tvDetailUsername.text = StringBuilder("@").append(user.username)
            tvDetailRepositoryCount.text = user.repository
            tvDetailFollowingCount.text = user.following
            tvDetailFollowersCount.text = user.followers
            tvDetailCompany.text = user.company
            tvDetailLocation.text = user.location
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}