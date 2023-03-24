package com.ssafy.kkaddak.presentation.wallet

import android.util.Log
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
        binding.apply {
            tvTotalBalance.visibility = View.VISIBLE
            tvNoWallet.visibility = View.GONE
        }
    }

    private fun initListener() {
        binding.apply {
            ivOpenWalletDialog.setOnClickListener {
                SetWalletDialog(requireActivity(), this@WalletFragment).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("plz", "onResume: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d("plz", "onStop: ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("plz", "onDestroyView: ")
    }

    private fun getBalance() {
        if (ApplicationClass.preferences.walletAddress.toString() == "") {
            binding.apply {
                tvTotalBalance.visibility = View.GONE
                tvNoWallet.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                tvTotalBalance.visibility = View.VISIBLE
                tvNoWallet.visibility = View.GONE
            }

            WalletFunction().balanceOf(
                ApplicationClass.preferences.walletAddress.toString(),
                binding.tvTotalBalance
            )
        }
    }
}
