package com.ssafy.kkaddak.presentation.profile

import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.FragmentOtherProfileBinding
import com.ssafy.kkaddak.presentation.MainActivity
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtherProfileFragment :
    BaseFragment<FragmentOtherProfileBinding>(R.layout.fragment_other_profile),
    CancelSubscribeDialogListener {

    private val args by navArgs<OtherProfileFragmentArgs>()
    private val profileViewModel by activityViewModels<ProfileViewModel>()

    override fun initView() {
        setData()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).hideBottomNavigation(false)
    }

    private fun initTabLayout() {
        val tabIcons =
            listOf(R.drawable.ic_profile_tab_song, R.drawable.ic_profile_tab_nft)
        binding.apply {
            vpProfile.adapter = ProfileAdapter(
                this@OtherProfileFragment,
                args.nickname,
                profileViewModel.profileData.value!!.isMine
            )
            TabLayoutMediator(
                tlProfile, vpProfile
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
            if (it!!.isFollowing == true) {
                binding.tvFollow.apply {
                    setBackgroundResource(R.drawable.bg_rect_indigo_to_han_purple_angle270_radius5)
                    text = "구독 취소"
                    setOnClickListener { view ->
                        cancelSubscribe(it.memberId)
                    }
                }
            } else {
                binding.tvFollow.apply {
                    setBackgroundResource(R.drawable.bg_rect_bitter_sweet_to_neon_pink_radius5)
                    text = "구독"
                    setOnClickListener { view ->
                        navigate(
                            OtherProfileFragmentDirections.actionOtherProfileFragmentToSubscribeFragment(
                                it.nickname,
                                it.profilepath,
                                it.account ?: "",
                                it.memberId
                            )
                        )
                    }
                }
            }
            initTabLayout()
        }
        profileViewModel.getProfileInfo(args.nickname)
    }

    private fun cancelSubscribe(artistId: String) {
        val dialog = CancelSubscribeDialog(requireContext(), artistId, this)
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onConfirmButtonClicked(artistId: String) {
        profileViewModel.unfollowArtist(artistId)
        setData()
        Toast.makeText(requireContext(), "구독이 취소되었습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun navigateToProfile(creatorId: String) {}
}