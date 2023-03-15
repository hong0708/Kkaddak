package com.ssafy.kkaddak.presentation.market

import android.widget.TextView
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.FragmentUploadMarketBinding
import com.ssafy.kkaddak.presentation.base.BaseFragment

class UploadMarketFragment :
    BaseFragment<FragmentUploadMarketBinding>(R.layout.fragment_upload_market) {
    override fun initView() {
        val tvUploadNft = view?.findViewById<TextView>(R.id.tv_select_nft_image)
        val tvUploadDeadline = view?.findViewById<TextView>(R.id.tv_select_deadline)
//        val tvDeadline = view?.findViewById<TextView>(R.id.tv_deadline)
        tvUploadNft?.setOnClickListener {
            // Show Dialog(dialog_upload_nft_detail)
        }
        tvUploadDeadline?.setOnClickListener {
            // Show Dialog(dialog_upload_nft_deadline)
            // tvDeadline.setText(마감날짜)
        }
    }
}