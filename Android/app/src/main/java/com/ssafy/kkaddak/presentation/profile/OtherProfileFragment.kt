package com.ssafy.kkaddak.presentation.profile

import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.FragmentOtherProfileBinding
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtherProfileFragment :
    BaseFragment<FragmentOtherProfileBinding>(R.layout.fragment_other_profile) {

    private val args by navArgs<OtherProfileFragmentArgs>()
    private val profileViewModel by activityViewModels<ProfileViewModel>()

    override fun initView() {
        setData()
    }

    private fun initTabLayout() {
        val tabIcons =
            listOf(R.drawable.ic_profile_tab_song, R.drawable.ic_profile_tab_nft)
        binding.apply {
            vpCuration.adapter = ProfileAdapter(
                this@OtherProfileFragment,
                args.nickname,
                profileViewModel.profileData.value!!.isMine
            )
            TabLayoutMediator(
                tlProfile, vpCuration
            ) { tab, position ->
                tab.setIcon(tabIcons[position])
                tab.icon?.setTintList(
                    ContextCompat.getColorStateList(
                        requireContext(),
                        R.color.selector_profile_tab_icon_color
                    )
                )
            }.attach()
        }
    }

    private fun setData() {
        profileViewModel.profileData.observe(viewLifecycleOwner) {
            binding.profile = it
            initTabLayout()
        }
        profileViewModel.getProfileInfo(args.nickname)
    }
}