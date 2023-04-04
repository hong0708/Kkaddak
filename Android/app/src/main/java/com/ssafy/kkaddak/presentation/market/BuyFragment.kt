package com.ssafy.kkaddak.presentation.market

import android.text.Editable
import android.text.TextWatcher
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
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

    override fun navigateToProfile(creatorId: String) {}

    private fun initListener() {
        binding.apply {
            tvPayment.setOnClickListener {
                if (state) {
                    try {
                        marketViewModel.closeMarket(args.marketId)
                        showToast("NFT 구매가 완료되었습니다.")
                        WalletFunction().transfer(args.sellerAccount, args.nftPrice.toLong(), "NFT 구매")
                        navigate(BuyFragmentDirections.actionBuyFragmentToMarketFragment())
                    } catch (e: Exception) {
                        e.printStackTrace()
                        showToast("NFT 구매에 실패하였습니다.")
                    }
                } else {
                    goWallet()
                }
            }
            tvWalletBalance.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(p0: Editable?) {
                    if (tvWalletBalance.text.toString().toDouble() > args.nftPrice.toDouble() / 100000000) {
                        state = true
                        tvPayment.setText(R.string.content_buy_payment)
                    } else {
                        state = false
                        tvPayment.setText(R.string.content_buy_charge)
                    }
                }
            })
        }
    }

    private fun getData() {
        marketViewModel.getBuyData(args)
        binding.apply {
            ivNftImage.setNormalImg(args.nftImagePath.toUri())
            tvCreatorNickname.text = args.nftCreator
            tvReceiverAddress.text = args.sellerAccount
            tvNftKat.text = String.format("%.1f", args.nftPrice.toDouble() / 100000000)

            setWidth()
        }
    }

    private fun setWidth() {
        binding.apply {
            val titleWidth = tvTitleCreator.measuredWidth * 0.8
            val nicknameWidth = tvCreatorNickname.measuredWidth
            if (nicknameWidth >= titleWidth) {
                var params = tvCreatorNickname.layoutParams as ConstraintLayout.LayoutParams
                params.startToEnd = ConstraintLayout.LayoutParams.UNSET
                params.topToTop = ConstraintLayout.LayoutParams.UNSET
                params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                params.topToBottom = tvTitleCreator.id
                tvCreatorNickname.layoutParams = params

                params = tvContentBuyNft.layoutParams as ConstraintLayout.LayoutParams
                params.topToBottom = tvCreatorNickname.id
            }
        }
    }

    private fun getBalance() {
        if (ApplicationClass.preferences.walletAddress.toString() == "") {
            state = false
            showToast("지갑 등록 또는 생성을 진행해주세요.")
            binding.tvPayment.setText(R.string.content_buy_charge)
        } else {
            WalletFunction().balanceOf(binding.tvWalletBalance)
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