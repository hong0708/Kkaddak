package com.ssafy.kkaddak.presentation.profile

import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.kkaddak.ApplicationClass
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.FragmentProfileBinding
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val profileViewModel by viewModels<ProfileViewModel>()

    override fun initView() {
        initTabLayout()
        setData()
    }

    private fun initTabLayout() {
        val tabIcons = listOf(R.drawable.ic_profile_tab_song, R.drawable.ic_profile_tab_nft)
        binding.apply {
            vpCuration.adapter = ProfileAdapter(this@ProfileFragment)
            TabLayoutMediator(
                tlProfile, vpCuration
            ) { tab, position ->
                tab.setIcon(tabIcons[position])
            }.attach()
        }
    }

    private fun setData() {
        profileViewModel.profileData.observe(viewLifecycleOwner) {
            binding.profile = it
        }
        profileViewModel.getProfileInfo(ApplicationClass.preferences.nickname!!)
    }
}