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
        setData()
    }

    private fun initListener() {
        binding.apply {
            ivUploadSong.setOnClickListener {
                if (ApplicationClass.preferences.walletAddress.toString() == "") {
                    showToast("지갑 등록이 필요합니다.")
                } else {
                    navigate(ProfileFragmentDirections.actionProfileFragmentToUploadSongFragment())
                }
            }
            ivBtnToMypage.setOnClickListener {
                navigate(ProfileFragmentDirections.actionProfileFragmentToMyPageFragment())
            }
            tvProfileFollowingCnt.setOnClickListener {
                navigate(ProfileFragmentDirections.actionProfileFragmentToMyFollowingFragment())
            }
            tvProfileFollowerCnt.setOnClickListener {
                navigate(ProfileFragmentDirections.actionProfileFragmentToMyFollowerFragment())
            }
            tvEditProfile.setOnClickListener {
                navigate(ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment())
            }
        }
    }

    private fun initTabLayout() {
        val tabIcons =
            listOf(R.drawable.ic_profile_tab_song, R.drawable.ic_profile_tab_nft)
        binding.apply {
            vpCuration.adapter = ProfileAdapter(
                this@ProfileFragment,
                ApplicationClass.preferences.nickname.toString(),
                profileViewModel.profileData.value!!.isMine,
                profileViewModel.profileData.value!!.account
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
            ApplicationClass.preferences.profileImg = it?.profilepath
            profileViewModel.profileImgStr.value = it?.profilepath
            if (it?.profilepath!!.isEmpty()) {
                profileViewModel.profileImgStr.value = null
                profileViewModel.profileImgUri.value = null
            }
            initTabLayout()
        }
        profileViewModel.getProfileInfo(ApplicationClass.preferences.nickname!!)
    }

    override fun navigateToProfile(creatorId: String) {
        navigate(
            ProfileFragmentDirections.actionProfileFragmentToOtherProfileFragment(
                creatorId
            )
        )
    }
}