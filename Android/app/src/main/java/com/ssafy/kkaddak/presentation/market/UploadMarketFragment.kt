package com.ssafy.kkaddak.presentation.market

import android.os.Bundle
import android.view.View
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.FragmentUploadMarketBinding
import com.ssafy.kkaddak.presentation.MainActivity
import com.ssafy.kkaddak.presentation.base.BaseFragment

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
            (activity as MainActivity).showNav()
        }
    }

    override fun initView() {
        (activity as MainActivity).hideNav()
        binding.tvSelectNftImage.setOnClickListener { }
        binding.tvSelectDeadline.setOnClickListener {
            val dialog = UploadDeadLineFragment()
            dialog.show(requireActivity().supportFragmentManager, "UploadDeadLineFragment")

            dialog.setFragmentInterfacer(object : UploadDeadLineFragment.FragmentInterfacer {
                //인터페이스 값 받아오기
                override fun onButtonClick(input: String) {
                    binding.tvUploadDeadline.setText(input)
                }
            })
        }
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).showNav()
    }
}