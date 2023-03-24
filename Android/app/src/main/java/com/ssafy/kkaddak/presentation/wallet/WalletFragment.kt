package com.ssafy.kkaddak.presentation.wallet

import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.common.util.WalletFunction
import com.ssafy.kkaddak.databinding.FragmentWalletBinding
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletFragment : BaseFragment<FragmentWalletBinding>(R.layout.fragment_wallet) {

    override fun initView() {
        initListener()
        getBalance()

    }

    private fun initListener() {

        binding.apply {
            clCharge.setOnClickListener {
                ChargeDialog(requireActivity()).show()
            }
        }
    }

    fun getBalance() {
        WalletFunction().balanceOf(
            "0xf10ccb49335c686147bdba507482bb3d3e3af1c4",
            binding.tvTotalBalance
        )
    }
}
