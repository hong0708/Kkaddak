package com.ssafy.kkaddak.presentation.market

import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.FragmentUploadMarketBinding
import com.ssafy.kkaddak.presentation.base.BaseFragment

class UploadMarketFragment :
    BaseFragment<FragmentUploadMarketBinding>(R.layout.fragment_upload_market) {

    override fun initView() {
        binding.tvSelectNftImage.setOnClickListener{  }
        binding.tvUploadDeadline.setOnClickListener {  }
    }
}