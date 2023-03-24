package com.ssafy.kkaddak.presentation.market

import android.os.Bundle
import android.view.View
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.FragmentUploadMarketBinding
import com.ssafy.kkaddak.domain.entity.market.UploadNftItem
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UploadMarketFragment :
    BaseFragment<FragmentUploadMarketBinding>(R.layout.fragment_upload_market) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        binding.ivBack.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host, MarketFragment())
                .addToBackStack(null)
                .commit()
        }
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
        binding.tvSelectDeadline.setOnClickListener {
            val dialog = DialogUploadNftDeadLineFragment()
            dialog.show(requireActivity().supportFragmentManager, "DialogUploadNftDeadLineFragment")

            dialog.setFragmentInterfacer(object : DialogUploadNftDeadLineFragment.FragmentInterfacer {
                override fun onButtonClick(input: String) {
                    binding.tvUploadDeadline.setText(input)
                }
            })
        }
    }

    override fun onPause() {
        super.onPause()
    }
}