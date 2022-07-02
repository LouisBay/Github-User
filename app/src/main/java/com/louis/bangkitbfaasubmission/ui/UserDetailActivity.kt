package com.louis.bangkitbfaasubmission.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.google.android.material.tabs.TabLayoutMediator
import com.louis.bangkitbfaasubmission.R
import com.louis.bangkitbfaasubmission.adapter.FollowsPagerAdapter
import com.louis.bangkitbfaasubmission.data.local.entity.UserFavoriteEntity
import com.louis.bangkitbfaasubmission.databinding.ActivityUserDetailBinding
import com.louis.bangkitbfaasubmission.data.remote.response.UserDetail
import com.louis.bangkitbfaasubmission.utils.Helper.checkString
import com.louis.bangkitbfaasubmission.utils.Helper.loadCircleImage
import com.louis.bangkitbfaasubmission.utils.Helper.showError
import com.louis.bangkitbfaasubmission.utils.Result
import com.louis.bangkitbfaasubmission.utils.ViewModelFactory
import com.louis.bangkitbfaasubmission.viewmodel.UserDetailViewModel

class UserDetailActivity : AppCompatActivity(), View.OnClickListener {

    private var username: String? = null
    private var isUserFavorite: Boolean = false
    private var userEntity: UserFavoriteEntity? = null
    private lateinit var activityDetailBinding: ActivityUserDetailBinding

    private val userDetailViewModel: UserDetailViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailBinding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding.root)

        username = intent.getStringExtra(EXTRA_USER)

        setToolbar()
        setTabLayout()
        setViewModel(username.toString())

        activityDetailBinding.fabFavorite.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_on_detail, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> onSupportNavigateUp()

            R.id.share -> {
                Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, getString(R.string.text_detail_share, username, username))
                    type = "text/plain"
                }.also { Intent.createChooser(it, null) }.also { startActivity(it) }
                true
            }

            R.id.open_web_profile -> {
                Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(getString(R.string.text_url_github, username))
                }.also { startActivity(it) }
                true
            }
            else -> true
        }
    }

    private fun setViewModel(username: String) {
        with(userDetailViewModel) {
            checkFavorite(username)

            isCalled.observe(this@UserDetailActivity) { called ->
                if (!called) getUserData(username)
            }

            result.observe(this@UserDetailActivity) { userData ->
                setResult(userData)
            }

            isFavorite.observe(this@UserDetailActivity) { result ->
                setButtonFavorite(result)
                isUserFavorite = result
            }
        }
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.fab_favorite -> {
                if (isUserFavorite) {
                    setButtonFavorite(false)
                    userEntity?.let { userDetailViewModel.deleteUserFromFavorite(it) }
                    Toast.makeText(applicationContext, "Successfully delete $username from Favorite", Toast.LENGTH_SHORT).show()
                } else {
                    setButtonFavorite(true)
                    userEntity?.let { userDetailViewModel.insertUserToFavorite(it) }
                    Toast.makeText(applicationContext, "Successfully add $username to Favorite", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setButtonFavorite(state: Boolean) {
        activityDetailBinding.fabFavorite.apply {
            if (state) setImageResource(R.drawable.ic_baseline_favorite_24)
            else setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        with(activityDetailBinding) {
            if (isLoading) {
                pgDetail.visibility = View.VISIBLE
                appbarDetail.visibility = View.INVISIBLE
                viewPager.visibility = View.INVISIBLE
                fabFavorite.visibility = View.INVISIBLE
            } else {
                pgDetail.visibility = View.GONE
                appbarDetail.visibility = View.VISIBLE
                viewPager.visibility = View.VISIBLE
                fabFavorite.visibility = View.VISIBLE
            }
        }
    }

    private fun setTabLayout() {
        val pagerAdapter = FollowsPagerAdapter(this, username.toString())

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

    private fun setResult(result: Result<UserDetail>) {
        when(result) {
            is Result.Loading -> showLoading(true)

            is Result.Success -> {
                val user = result.data
                parseUserDetail(user)

                UserFavoriteEntity(
                    user.login,
                    user.avatarUrl,
                    user.name.checkString(),
                    user.publicRepos
                ).let {
                    userEntity = it
                }

                showLoading(false)
            }

            is Result.Error -> showError(applicationContext, result.errorMessage)
        }
    }

    private fun parseUserDetail(user: UserDetail) {
        with(activityDetailBinding){
            tvTitleToolbar.text = getString(R.string.text_title_detail, user.login)

            tvDetailUsername.text = user.login
            tvDetailFollowersCount.text = user.followers.toString()
            tvDetailFollowingCount.text = user.following.toString()
            tvDetailRepositoryCount.text = user.publicRepos.toString()
            ivDetailAvatar.loadCircleImage(user.avatarUrl)

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