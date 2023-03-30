package com.ssafy.kkaddak.presentation.market

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.ssafy.kkaddak.ApplicationClass
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.common.util.BindingAdapters.setNormalImg
import com.ssafy.kkaddak.common.util.WalletFunction
import com.ssafy.kkaddak.databinding.ActivityMainBinding
import com.ssafy.kkaddak.databinding.FragmentBuyBinding
import com.ssafy.kkaddak.presentation.MainActivity
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BuyFragment : BaseFragment<FragmentBuyBinding>(R.layout.fragment_buy) {

    private val args by navArgs<BuyFragmentArgs>()
    private lateinit var navController: NavController
    private val marketViewModel by activityViewModels<MarketViewModel>()
    var state = false

    override fun initView() {
        (activity as MainActivity).hideBottomNavigation(true)
        initListener()
        getData()
        getBalance()
    }

    private fun initListener() {
        binding.apply {
            tvPayment.setOnClickListener {
                if (state) {
                    Log.d("payment", "결제")
                    // 결제 로직
                } else {
                    Log.d("payment", "충전")
                    // 충전 로직
                    goWallet()
                }
            }
        }
    }

    private fun getData() {
        marketViewModel.getBuyData(args)
        binding.apply {
            ivNftImage.setNormalImg(args.nftImagePath)
            tvCreatorNickname.text = args.nftCreator
            tvNftKat.text = args.nftPrice
        }
    }

    private fun getBalance() {
        if (ApplicationClass.preferences.walletAddress.toString() == "") {
            state = false
            binding.tvPayment.setText(R.string.content_buy_charge)
            // 충전 로직
        } else {
            WalletFunction().balanceOf(
                binding.tvWalletBalance
            )
            // 현재 잔액과 nft 가격 비교해서 구매/충전 결정
            binding.apply {
                if (tvWalletBalance.text.toString().toDouble() > args.nftPrice.toDouble()) {
                    Log.d("payment", ApplicationClass.preferences.walletAddress.toString())
                    state = true
                    tvPayment.setText(R.string.content_buy_payment)
                } else {
                    state = false
                    tvPayment.setText(R.string.content_buy_charge)
                }
            }
        }
    }

    fun goWallet() {
        // 다시 NFT 목록 화면으로 초기화
        popBackStack()
        popBackStack()

        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController

        ActivityMainBinding.inflate(layoutInflater).bottomNavigation.apply {
            setupWithNavController(navController)
            itemIconTintList = null
            selectedItemId = R.id.walletFragment
        }
    }
}