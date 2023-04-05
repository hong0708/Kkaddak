package com.ssafy.kkaddak.presentation.profile

import android.text.Editable
import android.text.TextWatcher
import android.view.View
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
        initListener()
    }

    private fun initListener() {
        binding.apply {
            tvWalletBalance.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (tvWalletBalance.text.toString().toFloat() >= 5) {
                        tvPayment.apply {
                            visibility = View.VISIBLE
                            tvBalanceLack.visibility = View.GONE
                            setOnClickListener {
                                profileViewModel.followArtist(args.memberId)
                                // 구독 시 결제 진행
                                WalletFunction().transfer(
                                    args.address,
                                    (5 * 100000000).toLong(),
                                    "구독"
                                )
                                popBackStack()
                            }
                        }
                    } else {
                        binding.tvBalanceLack.visibility = View.VISIBLE
                        showToast("잔액이 부족합니다.")
                    }
                }
            })
        }
    }

    private fun setData() {
        binding.apply {
            tvCreatorNickname.text = args.nickname
            ivProfileImage.setProfileImg(args.profileImg)
            tvReceiverAddress.text = args.address

            if (ApplicationClass.preferences.walletAddress.toString() == "") {
                showToast("지갑 등록 또는 생성을 진행해주세요.")
            } else {
                WalletFunction().balanceOf(tvWalletBalance)
            }
        }
    }

    override fun navigateToProfile(creatorId: String) {
        navigate(SubscribeFragmentDirections.actionSubscribeFragmentToOtherProfileFragment(creatorId))
    }
}