package com.louis.bangkitbfaasubmission.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.louis.bangkitbfaasubmission.adapter.FollowsPagerAdapter
import com.louis.bangkitbfaasubmission.adapter.ListUserAdapter
import com.louis.bangkitbfaasubmission.databinding.FragmentFollowListBinding
import com.louis.bangkitbfaasubmission.data.remote.response.UserItem
import com.louis.bangkitbfaasubmission.utils.Helper.showError
import com.louis.bangkitbfaasubmission.utils.Result
import com.louis.bangkitbfaasubmission.utils.ViewModelFactory
import com.louis.bangkitbfaasubmission.viewmodel.FollowViewModel

class FollowFragment : Fragment() {

    private var username: String? = null
    private var type: String? = null
    private var _binding: FragmentFollowListBinding? = null
    private val binding get() = _binding!!

    private val followersViewModel: FollowViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowListBinding.inflate(inflater, container, false)
    
        getDataFromArgument()
        setViewModel()
        return binding.root
    }


    override fun onDestroy() {
        _binding = null
        username = null
        type = null
        super.onDestroy()
    }

    private fun getDataFromArgument() {
        arguments?.let {
            username = it.getString(FollowsPagerAdapter.USERNAME_ARG)
            type = it.getString(FollowsPagerAdapter.TYPE_ARG)
        }
    }

    private fun setViewModel(){
        with(followersViewModel) {

            isCalled.observe(viewLifecycleOwner) { called ->
                if (!called) {
                    if (type == "followers") getUserFollowers(username.toString())
                    else if (type == "following") getUserFollowing(username.toString())
                }
            }

            result.observe(viewLifecycleOwner) { result ->
                showFollowList(result)
            }
        }
    }

    private fun showNotFoundOrLoading(state: Boolean, view: View) {
        with(binding) {
            if (state){
                rvListFollow.visibility = View.INVISIBLE
                view.visibility = View.VISIBLE
            } else {
                rvListFollow.visibility = View.VISIBLE
                view.visibility = View.GONE
            }
        }
    }

    private fun showFollowList(result: Result<ArrayList<UserItem>>) {
        when (result) {
            is Result.Loading -> showNotFoundOrLoading(true, binding.pgFragment)

            is Result.Success -> {
                if (result.data.size == 0) {
                    showNotFoundOrLoading(true, binding.tvNoResult)
                    showNotFoundOrLoading(false, binding.pgFragment)
                } else {
                    val listUserAdapter = ListUserAdapter(result.data)
                    with(binding.rvListFollow) {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(context)
                        adapter = listUserAdapter
                    }

                    listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
                        override fun onItemClicked(data: UserItem) {
                            toDetailUser(data)
                        }
                    })
                    showNotFoundOrLoading(false, binding.pgFragment)
                }
            }

            is Result.Error -> showError(requireContext(), result.errorMessage)
        }
    }

    private fun toDetailUser(user: UserItem) {
        val intentToDetail = Intent(context, UserDetailActivity::class.java)
        intentToDetail.putExtra(UserDetailActivity.EXTRA_USER, user.login)
        startActivity(intentToDetail)
    }
}