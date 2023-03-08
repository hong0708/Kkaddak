package com.ssafy.kkaddak.presentation.profile

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ProfileAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) ProfileSongFragment()
        else ProfileNFTFragment()
    }
}