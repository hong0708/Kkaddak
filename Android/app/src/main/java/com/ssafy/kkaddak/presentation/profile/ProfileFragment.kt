package com.ssafy.kkaddak.presentation.profile

import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.kkaddak.ApplicationClass
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.FragmentProfileBinding
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val profileViewModel by activityViewModels<ProfileViewModel>()

    override fun initView() {
        initListener()
        initTabLayout()
        setData()
    }

    private fun initListener() {
        binding.ivUploadSong.setOnClickListener {
            navigate(ProfileFragmentDirections.actionProfileFragmentToUploadSongFragment())
        }
        binding.ivBtnToMypage.setOnClickListener {
            navigate(ProfileFragmentDirections.actionProfileFragmentToMyPageFragment())
        }
    }

    private fun initTabLayout() {
        val tabIcons =
            listOf(R.drawable.ic_profile_tab_song, R.drawable.ic_profile_tab_nft)
        binding.apply {
            vpCuration.adapter = ProfileAdapter(this@ProfileFragment)
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
        }
        profileViewModel.getProfileInfo(ApplicationClass.preferences.nickname!!)
    }
}