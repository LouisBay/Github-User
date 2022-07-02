package com.louis.bangkitbfaasubmission.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.louis.bangkitbfaasubmission.ui.FollowFragment

class FollowsPagerAdapter(activity: AppCompatActivity, private val username: String) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        when(position) {
            0 -> fragment = FollowFragment().apply {
                arguments = Bundle().apply {
                    putString(USERNAME_ARG, username)
                    putString(TYPE_ARG, "followers")
                }
            }
            1 -> fragment = FollowFragment().apply {
                arguments = Bundle().apply {
                    putString(USERNAME_ARG, username)
                    putString(TYPE_ARG, "following")
                }
            }
        }

        return fragment as Fragment
    }

    override fun getItemCount() = 2

    companion object {
        const val USERNAME_ARG = "username"
        const val TYPE_ARG = "type"
    }
}

