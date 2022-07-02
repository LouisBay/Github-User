package com.louis.bangkitbfaasubmission.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.louis.bangkitbfaasubmission.R
import com.louis.bangkitbfaasubmission.adapter.ListUserAdapter
import com.louis.bangkitbfaasubmission.data.remote.response.UserItem
import com.louis.bangkitbfaasubmission.databinding.ActivityMainBinding
import com.louis.bangkitbfaasubmission.utils.Helper.showError
import com.louis.bangkitbfaasubmission.utils.Result
import com.louis.bangkitbfaasubmission.utils.ViewModelFactory
import com.louis.bangkitbfaasubmission.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setToolbar()
        setViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_on_home, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        
        with(searchView){
            maxWidth = Integer.MAX_VALUE
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            queryHint = getString(R.string.search_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    activityMainBinding.textNotFound.visibility = View.GONE
                    mainViewModel.searchUser(query)
                    clearFocus()
                    showResultText(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })

        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite -> {
                Intent(this@MainActivity, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
                true
            }
            R.id.settings -> {
                Intent(this@MainActivity, SettingsActivity::class.java).also {
                    startActivity(it)
                }
                true
            }
            else -> true
        }
    }

    private fun setToolbar(){
        setSupportActionBar(activityMainBinding.toolbarMain)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setViewModel(){
        with(mainViewModel) {
            result.observe(this@MainActivity) { result ->
                setSearchData(result)
            }

            isSearching.observe(this@MainActivity) { searching ->
                if (searching) querySearch.observe(this@MainActivity) { showResultText(it) }
            }
        }
    }

    private fun setSearchData(result: Result<ArrayList<UserItem>>) {
        when (result) {
            is Result.Loading -> showNotFoundOrLoading(true, activityMainBinding.pgMain)

            is Result.Success -> {
                setRvResult(result.data)
                showNotFoundOrLoading(result.data.size == 0, activityMainBinding.textNotFound)
                showNotFoundOrLoading(false, activityMainBinding.pgMain)
            }

            is Result.Error -> showError(applicationContext, result.errorMessage)
        }

    }

    private fun setRvResult(data: ArrayList<UserItem>) {
        val listUserAdapter = ListUserAdapter(data)
        with(activityMainBinding.rvUsers) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = listUserAdapter
        }

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserItem) {
                toDetailUser(data)
            }
        })
    }

    private fun toDetailUser(user: UserItem) {
        val intentToDetail = Intent(this@MainActivity, UserDetailActivity::class.java)
        intentToDetail.putExtra(UserDetailActivity.EXTRA_USER, user.login)
        startActivity(intentToDetail)
    }

    private fun showNotFoundOrLoading(state: Boolean, view: View) {
        with(activityMainBinding) {
            if (state) {
                rvUsers.visibility = View.INVISIBLE
                view.visibility = View.VISIBLE
            } else {
                rvUsers.visibility = View.VISIBLE
                view.visibility = View.GONE
            }
        }
    }

    private fun showResultText(query: String){
        with (activityMainBinding.textResult) {
            visibility = View.VISIBLE
            text = getString(R.string.text_result, query)
        }
    }
}

