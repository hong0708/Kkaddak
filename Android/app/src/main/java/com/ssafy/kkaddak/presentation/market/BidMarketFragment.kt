package com.ssafy.kkaddak.presentation.market

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.FragmentBidMarketBinding
import com.ssafy.kkaddak.presentation.MainActivity
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BidMarketFragment :
    BaseFragment<FragmentBidMarketBinding>(R.layout.fragment_bid_market) {

    private val args by navArgs<BidMarketFragmentArgs>()
    private val marketViewModel by activityViewModels<MarketViewModel>()

    override fun initView() {
        (activity as MainActivity).HideBottomNavigation(true)
        observeData()
        initListener()
        getData()
    }

    private fun getData() {
        marketViewModel.nftData.observe(viewLifecycleOwner) {
            binding.nftItem = it
        }
        marketViewModel.getData(args.nftItem)
    }

    private fun initListener() {
        binding.ivBack.setOnClickListener { popBackStack() }
        binding.ivBidNftLike.setOnClickListener {
            if(marketViewModel.nftData.value!!.isLike) {
                marketViewModel.cancelBookmark(marketViewModel.nftData.value!!.auctionId)
            } else {
                marketViewModel.requestBookmark(marketViewModel.nftData.value!!.auctionId)
            }
        }
        binding.clUpload.setOnClickListener {
        //입찰 진행
        }

    }

    private fun observeData() {
        marketViewModel.nftData.observe(viewLifecycleOwner) {
            if (it!!.isLike) {
                binding.ivBidNftLike.setImageResource(R.drawable.ic_market_like_selected)
            } else {
                binding.ivBidNftLike.setImageResource(R.drawable.ic_market_like)
            }
        }
    }
}