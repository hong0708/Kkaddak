package com.ssafy.kkaddak.presentation.profile

import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.ssafy.kkaddak.ApplicationClass
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.common.util.BindingAdapters.setProfileImg
import com.ssafy.kkaddak.common.util.WalletFunction
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
            WalletFunction().balanceOf(tvWalletBalance)
            tvPayment.setOnClickListener {
                if (ApplicationClass.preferences.walletAddress.toString() == "") {
                    Toast.makeText(requireContext(), "지갑 등록 또는 생성을 진행해주세요.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    if (tvWalletBalance.text.toString().toFloat() > 1) {
                        binding.tvBalanceLack.visibility = View.GONE
                        profileViewModel.followArtist(args.memberId)
                        // 구독 시 결제 진행
                        WalletFunction().transfer(args.address, 1, "구독")
                        popBackStack()
                    } else {
                        binding.tvBalanceLack.visibility = View.VISIBLE
                        Toast.makeText(requireContext(), "잔액이 부족합니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    override fun navigateToProfile(creatorId: String) {
        navigate(SubscribeFragmentDirections.actionSubscribeFragmentToOtherProfileFragment(creatorId))
    }
}