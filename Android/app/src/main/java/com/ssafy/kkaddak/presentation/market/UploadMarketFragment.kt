package com.ssafy.kkaddak.presentation.market

import android.os.Bundle
import android.view.View
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.FragmentUploadMarketBinding
import com.ssafy.kkaddak.domain.entity.market.UploadNftItem
import com.ssafy.kkaddak.presentation.MainActivity
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UploadMarketFragment :
    BaseFragment<FragmentUploadMarketBinding>(R.layout.fragment_upload_market) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).hideBottomNavigation(true)
        initView()
        binding.ivBack.setOnClickListener { popBackStack() }
    }

    override fun initView() {
        binding.tvSelectNftImage.setOnClickListener {
            val dialog = DialogUploadNftDetailFragment()
            dialog.show(requireActivity().supportFragmentManager, "DialogUploadNftDetailFragment")

            dialog.setFragmentInterfacer(object : DialogUploadNftDetailFragment.FragmentInterfacer {
                override fun onSendNftInfo(input: UploadNftItem) {
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).hideBottomNavigation(false)
    }
}