package com.louis.bangkitbfaasubmission.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.louis.bangkitbfaasubmission.ui.detail.FollowersFragment
import com.louis.bangkitbfaasubmission.ui.detail.FollowingFragment

class FollowsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        when(position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }

        return fragment as Fragment
    }

    override fun getItemCount() = 2
}