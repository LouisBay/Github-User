package com.louis.bangkitbfaasubmission.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.google.android.material.tabs.TabLayoutMediator
import com.louis.bangkitbfaasubmission.R
import com.louis.bangkitbfaasubmission.adapter.FollowsPagerAdapter
import com.louis.bangkitbfaasubmission.checkString
import com.louis.bangkitbfaasubmission.databinding.ActivityUserDetailBinding
import com.louis.bangkitbfaasubmission.loadCircleImage
import com.louis.bangkitbfaasubmission.model.UserDetail
import com.louis.bangkitbfaasubmission.viewmodel.UserDetailViewModel

class UserDetailActivity : AppCompatActivity() {

    private lateinit var activityDetailBinding: ActivityUserDetailBinding
    private val userDetailViewModel by viewModels<UserDetailViewModel>()
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailBinding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding.root)

        username = intent.getStringExtra(EXTRA_USER)

        setToolbar()
        setTabLayout()
        setViewModel(username.toString())
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setViewModel(username: String) {
        with(userDetailViewModel) {
            isCalled.observe(this@UserDetailActivity) { called ->
                if (!called) getUserData(username)
            }

            userData.observe(this@UserDetailActivity) { userData ->
                parseUserData(userData)
            }

            isLoading.observe(this@UserDetailActivity) {
                showLoading(it)
            }

        }
    }

    private fun showLoading(isLoading: Boolean) {
        with(activityDetailBinding) {
            if (isLoading) {
                pgDetail.visibility = View.VISIBLE
                appbarDetail.visibility = View.INVISIBLE
                viewPager.visibility = View.INVISIBLE
            } else {
                pgDetail.visibility = View.GONE
                appbarDetail.visibility = View.VISIBLE
                viewPager.visibility = View.VISIBLE
            }
        }
    }

    private fun setTabLayout() {
        val pagerAdapter = FollowsPagerAdapter(this)

        val viewPager = activityDetailBinding.viewPager
        viewPager.adapter = pagerAdapter

        val tabLayout = activityDetailBinding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun setToolbar() {
        setSupportActionBar(activityDetailBinding.toolbarDetail)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }


    private fun parseUserData(user: UserDetail) {
        with(activityDetailBinding){
            tvTitleToolbar.text = getString(R.string.text_title_detail, user.login)

            tvDetailUsername.text = user.login
            tvDetailFollowersCount.text = user.followers.toString()
            tvDetailFollowingCount.text = user.following.toString()
            tvDetailRepositoryCount.text = user.publicRepos.toString()
            ivDetailAvatar.loadCircleImage(user.avatarUrl.toString())

            tvDetailName.text = user.name.checkString()
            tvDetailCompany.text = user.company.checkString()
            tvDetailLocation.text = user.location.checkString()
            tvDetailBlog.text = user.blog.checkString()
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.text_followers,
            R.string.text_following
        )
    }
}