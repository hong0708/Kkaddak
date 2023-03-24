package com.ssafy.kkaddak.presentation.wallet

import android.view.View
import com.ssafy.kkaddak.ApplicationClass
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.common.util.WalletFunction
import com.ssafy.kkaddak.databinding.FragmentWalletBinding
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletFragment : BaseFragment<FragmentWalletBinding>(R.layout.fragment_wallet),
    SetWalletDialogInterface {

    override fun initView() {
        initListener()
        getBalance()
    }

    override fun setWallet(walletAddress: String, privateKey: String) {
        WalletFunction().insertUserWallet(walletAddress, privateKey, binding.tvTotalBalance)
        visibility(true)
    }

    private fun initListener() {
        binding.apply {
            ivOpenWalletDialog.setOnClickListener {
                SetWalletDialog(requireActivity(), this@WalletFragment).show()
            }
        }
    }

    private fun getBalance() {
        if (ApplicationClass.preferences.walletAddress.toString() == "") visibility(false)
        else {
            visibility(true)
            WalletFunction().balanceOf(
                ApplicationClass.preferences.walletAddress.toString(),
                binding.tvTotalBalance
            )
        }
    }

    private fun visibility(wallet: Boolean) {
        binding.apply {
            if (wallet) {
                tvTotalBalance.visibility = View.VISIBLE
                tvNoWallet.visibility = View.GONE
            } else {
                tvTotalBalance.visibility = View.GONE
                tvNoWallet.visibility = View.VISIBLE
            }
        }
    }
}
