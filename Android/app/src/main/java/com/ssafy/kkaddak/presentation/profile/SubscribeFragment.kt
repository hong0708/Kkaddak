package com.ssafy.kkaddak.presentation.profile

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.common.util.BindingAdapters.setProfileImg
import com.ssafy.kkaddak.databinding.FragmentSubscribeBinding
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubscribeFragment :
    BaseFragment<FragmentSubscribeBinding>(R.layout.fragment_subscribe) {

    private val args by navArgs<SubscribeFragmentArgs>()
    private val profileViewModel by activityViewModels<ProfileViewModel>()

    override fun initView() {
        setData()
    }

    private fun setData() {
        binding.apply {
            tvCreatorNickname.text = args.nickname
            ivProfileImage.setProfileImg(args.profileImg)
            tvReceiverAddress.text = args.address
            tvPayment.setOnClickListener { profileViewModel.followArtist(args.memberId) }
        }
    }

    override fun navigateToProfile(creatorId: String) {
        navigate(SubscribeFragmentDirections.actionSubscribeFragmentToOtherProfileFragment(creatorId))
    }
}