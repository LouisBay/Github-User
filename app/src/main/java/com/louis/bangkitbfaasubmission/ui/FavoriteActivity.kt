package com.louis.bangkitbfaasubmission.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.louis.bangkitbfaasubmission.R
import com.louis.bangkitbfaasubmission.adapter.FavoriteUserAdapter
import com.louis.bangkitbfaasubmission.data.local.entity.UserFavoriteEntity
import com.louis.bangkitbfaasubmission.databinding.ActivityFavoriteBinding
import com.louis.bangkitbfaasubmission.utils.ViewModelFactory
import com.louis.bangkitbfaasubmission.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private var countFav: Int = 0
    private lateinit var activityFavoriteBinding: ActivityFavoriteBinding

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(activityFavoriteBinding.root)

        setToolbar()
        setViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_on_favorite, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> onSupportNavigateUp()
            R.id.delete_all -> {
                if (countFav > 0) showAlertDialog()
                else Toast.makeText(applicationContext, "No user found in Favorite", Toast.LENGTH_SHORT).show()
                true
            }
            else -> true
        }
    }

    private fun setToolbar() {
        setSupportActionBar(activityFavoriteBinding.toolbarFavorite)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun setViewModel() {
        with(favoriteViewModel) {
            getAllFavoriteUser().observe(this@FavoriteActivity) { data ->
                setRvFavorite(data)
                countFav = data.size
                if (countFav == 0) showNotFound(true) else showNotFound(false)
            }
        }
    }

    private fun setRvFavorite(list: List<UserFavoriteEntity>) {
        val userFavAdapter = FavoriteUserAdapter(list)
        with(activityFavoriteBinding.rvFavorite) {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@FavoriteActivity,2)
            adapter = userFavAdapter
        }

        userFavAdapter.setOnItemClickCallback(object : FavoriteUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserFavoriteEntity) {
                toDetailUser(data)
            }
        })
    }

    private fun toDetailUser(user: UserFavoriteEntity) {
        val intentToDetail = Intent(this@FavoriteActivity, UserDetailActivity::class.java)
        intentToDetail.putExtra(UserDetailActivity.EXTRA_USER, user.username)
        startActivity(intentToDetail)
    }

    private fun showAlertDialog() {
        MaterialAlertDialogBuilder(this@FavoriteActivity)
            .setTitle(resources.getString(R.string.text_delete_all))
            .setMessage(resources.getString(R.string.text_message_alert))
            .setIcon(R.drawable.ic_baseline_delete_sweep_24)
            .setPositiveButton("Yes") {_, _ ->
                favoriteViewModel.deleteAllFavoriteUser()
                Toast.makeText(applicationContext, "Successfully delete all user from Favorite", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { dialog, _ -> dialog.cancel() }
            .setCancelable(true)
            .show()
    }

    private fun showNotFound(state: Boolean) {
        with(activityFavoriteBinding) {
            if (state){
                rvFavorite.visibility = View.INVISIBLE
                tvNoResult.visibility = View.VISIBLE
            } else {
                rvFavorite.visibility = View.VISIBLE
                tvNoResult.visibility = View.GONE
            }
        }
    }
}