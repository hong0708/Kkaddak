package com.ssafy.kkaddak.presentation.profile

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ProfileAdapter(
    fragment: Fragment,
    private val nickname: String,
    private val isMine: Boolean,
    private val account: String?,
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            val args = ProfileSongFragmentArgs(nickname, isMine)
            ProfileSongFragment().apply {
                arguments = args.toBundle()
            }
        } else {
            val args = ProfileNFTFragmentArgs(nickname, isMine, account)
            ProfileNFTFragment().apply {
                arguments = args.toBundle()
            }
        }
    }
}