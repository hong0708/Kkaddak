package com.ssafy.kkaddak.presentation.wallet

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.kkaddak.ApplicationClass
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.common.util.WalletFunction
import com.ssafy.kkaddak.databinding.FragmentWalletBinding
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kr.co.bootpay.android.Bootpay
import kr.co.bootpay.android.events.BootpayEventListener
import kr.co.bootpay.android.models.BootExtra
import kr.co.bootpay.android.models.BootItem
import kr.co.bootpay.android.models.Payload

@AndroidEntryPoint
class WalletFragment : BaseFragment<FragmentWalletBinding>(R.layout.fragment_wallet),
    SetWalletDialogInterface {

    private val walletViewModel by viewModels<WalletViewModel>()
    private val recentTransactionListAdapter by lazy { RecentTransactionListAdapter() }

    override fun initView() {
        initListener()
        getBalance()
        initRecyclerView()
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

    override fun chargeCoin(amount: Double) {
        val items: MutableList<BootItem> = ArrayList()
        items.add(BootItem().setName("결제권").setId("1234").setQty(1).setPrice(amount))
        val payload = Payload()

        payload.setApplicationId(getString(R.string.APPLICATION_ID))
            .setOrderName("까딱까딱 구독권 결제")
            .setPg("kcp")
            .setOrderId("1234")
            .setPrice(amount)
            .setExtra(BootExtra()).items = items

        val map: MutableMap<String, Any> = HashMap()
        map["account_address"] = ApplicationClass.preferences.walletAddress.toString()
        payload.metadata = map

        Bootpay.init(requireActivity().supportFragmentManager, requireActivity().applicationContext)
            .setPayload(payload)
            .setEventListener(object : BootpayEventListener {
                override fun onCancel(data: String) {
                    Log.d("bootpay", "cancel: $data")
                }

                override fun onError(data: String) {
                    Log.d("bootpay", "error: $data")
                }

                override fun onClose() {
                    Bootpay.removePaymentWindow()
                }

                override fun onIssued(data: String) {
                    Log.d("bootpay", "issued: $data")
                }

                override fun onConfirm(data: String): Boolean {
                    Log.d("bootpay", "confirm: $data")
                    return true
                }

                override fun onDone(data: String) {
                    Toast.makeText(requireContext(), "충전되었습니다.", Toast.LENGTH_SHORT).show()
                    // 완료 정보 서버 연결
                }
            }).requestPayment()
    }

    private fun initRecyclerView() {
        binding.rvRecentTransactionList.apply {
            adapter = recentTransactionListAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }

        if (ApplicationClass.preferences.walletAddress.toString() != "") {
            WalletFunction().getRecentTransactionList().observe(viewLifecycleOwner) { lists ->
                recentTransactionListAdapter.setData(lists)
            }
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
                    ChargeCoinDialog(requireActivity(), this@WalletFragment).show()
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

    override fun navigateToProfile(creatorId: String) {
        navigate(WalletFragmentDirections.actionWalletFragmentToOtherProfileFragment(creatorId))
    }
}
