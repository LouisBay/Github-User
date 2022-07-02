package com.louis.bangkitbfaasubmission.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.louis.bangkitbfaasubmission.adapter.ListUserAdapter
import com.louis.bangkitbfaasubmission.R
import com.louis.bangkitbfaasubmission.databinding.ActivityMainBinding
import com.louis.bangkitbfaasubmission.model.User

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private val list = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "GitHub User's"

        list.addAll(listUsers)
        setRecyclerView()

    }

    private fun setRecyclerView() {
        val listUserAdapter = ListUserAdapter(list)
        with(binding) {
            rvUsers.setHasFixedSize(true)
            rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUsers.adapter = listUserAdapter
        }

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intentToDetail = Intent(this@MainActivity, UserDetailActivity::class.java)
                intentToDetail.putExtra(UserDetailActivity.EXTRA_USER, data)
                startActivity(intentToDetail)
            }
        })
    }


    private val listUsers: ArrayList<User>
        get() {
            val listUser = ArrayList<User>()
            with(resources) {
                val dataUsername = getStringArray(R.array.username)
                val dataName = getStringArray(R.array.name)
                val dataLocation = getStringArray(R.array.location)
                val dataRepository = getStringArray(R.array.repository)
                val dataCompany = getStringArray(R.array.company)
                val dataFollowers = getStringArray(R.array.followers)
                val dataFollowing = getStringArray(R.array.following)
                val dataAvatar = obtainTypedArray(R.array.avatar)

                for (i in dataName.indices) {
                    val user = User(
                        dataUsername[i],
                        dataName[i],
                        dataLocation[i],
                        dataRepository[i],
                        dataCompany[i],
                        dataFollowers[i],
                        dataFollowing[i],
                        dataAvatar.getResourceId(i,-1)
                    )
                    listUser.add(user)
                }
                dataAvatar.recycle()
            }
            return listUser
        }
}

