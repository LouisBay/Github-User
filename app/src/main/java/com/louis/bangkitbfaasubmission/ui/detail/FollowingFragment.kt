package com.louis.bangkitbfaasubmission.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.louis.bangkitbfaasubmission.adapter.ListUserAdapter
import com.louis.bangkitbfaasubmission.databinding.FragmentFollowListBinding
import com.louis.bangkitbfaasubmission.model.UserItem
import com.louis.bangkitbfaasubmission.viewmodel.FollowFragmentViewModel

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowListBinding? = null
    private val binding get() = _binding!!
    private val followingViewModel by viewModels<FollowFragmentViewModel>()
    private var username: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowListBinding.inflate(inflater, container, false)

        username = activity?.intent?.getStringExtra(UserDetailActivity.EXTRA_USER)

        setViewModel()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setViewModel(){
        with(followingViewModel) {
            isCalled.observe(viewLifecycleOwner) { called ->
                if (!called) getUserFollowing(username.toString())
            }

            listUsers.observe(viewLifecycleOwner) { list ->
                if (list.size > 0) {
                    setFollowingList(list)
                    showNotFoundOrLoading(false, binding.tvNoResult)
                }
                else {
                    showNotFoundOrLoading(true, binding.tvNoResult)
                }
            }

            isLoading.observe(viewLifecycleOwner) { showNotFoundOrLoading(it, binding.pgFragment) }
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


    private fun setFollowingList(list: ArrayList<UserItem>) {
        val listUserAdapter = ListUserAdapter(list)
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
    }

    private fun toDetailUser(user: UserItem) {
        val intentToDetail = Intent(context, UserDetailActivity::class.java)
        intentToDetail.putExtra(UserDetailActivity.EXTRA_USER, user.login)
        startActivity(intentToDetail)
    }
}