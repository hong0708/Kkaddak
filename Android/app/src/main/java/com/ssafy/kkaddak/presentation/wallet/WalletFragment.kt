package com.ssafy.kkaddak.presentation.wallet

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.kkaddak.ApplicationClass
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.common.util.NFTFunction
import com.ssafy.kkaddak.common.util.WalletFunction
import com.ssafy.kkaddak.databinding.FragmentWalletBinding
import com.ssafy.kkaddak.domain.entity.wallet.RecentTransactionItem
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletFragment : BaseFragment<FragmentWalletBinding>(R.layout.fragment_wallet),
    SetWalletDialogInterface {

    private var recentTransactionList = ArrayList<RecentTransactionItem>()
    private val walletViewModel by viewModels<WalletViewModel>()
    private val recentTransactionListAdapter by lazy { RecentTransactionListAdapter() }

    override fun initView() {
        initListener()
        getBalance()
        initRecyclerView()

        if (ApplicationClass.preferences.walletAddress.toString() != "") {
            val lists = WalletFunction().getRecentTransactionList()
            for (i in lists) {
                recentTransactionList.add(
                    RecentTransactionItem(
                        i.sender,
                        i.recipient,
                        i.timeStamp,
                        i.amount,
                        i.transferType
                    )
                )
            }
        }
        Log.d("ghdalsrl", "initView: list ${recentTransactionList}")
    }

    override fun setWallet(walletAddress: String, privateKey: String) {
        WalletFunction().insertUserWallet(walletAddress, privateKey, binding.tvTotalBalance)
        visibility(true)
        walletViewModel.registerWalletAccount(walletAddress)
    }

    override fun generateWallet() {
        WalletFunction().generateWallet(binding.tvTotalBalance)
        visibility(true)
        walletViewModel.registerWalletAccount(
            String(
                ApplicationClass.keyStore.decryptData(
                    WalletFunction().decode(ApplicationClass.preferences.walletAddress.toString())
                )
            )
        )
    }

    private fun initRecyclerView() {
        binding.rvRecentTransactionList.apply {
            adapter = recentTransactionListAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    private fun initListener() {
        binding.apply {
            ivOpenWalletDialog.setOnClickListener {
                SetWalletDialog(requireActivity(), this@WalletFragment).show()
            }

            clWalletInfo.setOnClickListener {
                WalletInfoDialog(requireActivity()).show()
            }

            clCharge.setOnClickListener {
                if (ApplicationClass.preferences.walletAddress.toString() == "") {
                    Toast.makeText(requireContext(), "지갑 등록 또는 생성을 진행해주세요.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    // 충전 플로우
                }
            }
        }
    }

    private fun getBalance() {
        if (ApplicationClass.preferences.walletAddress.toString() == "") visibility(false)
        else {
            visibility(true)
            WalletFunction().balanceOf(
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
