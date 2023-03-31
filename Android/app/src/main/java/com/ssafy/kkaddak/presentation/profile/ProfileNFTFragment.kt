package com.ssafy.kkaddak.presentation.profile

import android.util.Log
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.common.util.NFTFunction
import com.ssafy.kkaddak.databinding.FragmentProfileNftBinding
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileNFTFragment :
    BaseFragment<FragmentProfileNftBinding>(R.layout.fragment_profile_nft){

    override fun initView() {
        NFTFunction().getNFTCount()
        NFTFunction().getTokensOfOwner()
        Log.d("홍민기", "initView:  ${NFTFunction().getTokensOfOwner()}")
    }

    override fun navigateToProfile(creatorId: String) {

    }
}