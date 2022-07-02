package com.louis.bangkitbfaasubmission.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.louis.bangkitbfaasubmission.adapter.ListUserAdapter
import com.louis.bangkitbfaasubmission.R
import com.louis.bangkitbfaasubmission.databinding.ActivityMainBinding
import com.louis.bangkitbfaasubmission.model.UserItem
import com.louis.bangkitbfaasubmission.ui.detail.UserDetailActivity
import com.louis.bangkitbfaasubmission.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setToolbar()
        setViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

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

    private fun setToolbar(){
        setSupportActionBar(activityMainBinding.toolbarMain)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setSearchData(searchResult: ArrayList<UserItem>) {
        val listUserAdapter = ListUserAdapter(searchResult)
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

    private fun setViewModel(){
        with(mainViewModel) {
            listUserSearch.observe(this@MainActivity) { items ->
                setSearchData(items)
            }

            isNotFound.observe(this@MainActivity) { notFound ->
                showNotFoundOrLoading(notFound, activityMainBinding.textNotFound)
            }

            isSearching.observe(this@MainActivity) { searching ->
                if (searching) querySearch.observe(this@MainActivity) { showResultText(it) }
            }

            isLoading.observe(this@MainActivity) { showNotFoundOrLoading(it, activityMainBinding.pgMain) }
        }
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

