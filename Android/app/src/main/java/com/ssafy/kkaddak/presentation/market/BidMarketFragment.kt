package com.ssafy.kkaddak.presentation.market

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.FragmentBidMarketBinding
import com.ssafy.kkaddak.presentation.MainActivity
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BidMarketFragment :
    BaseFragment<FragmentBidMarketBinding>(R.layout.fragment_bid_market) {

    private val args by navArgs<BidMarketFragmentArgs>()
    private val marketViewModel by activityViewModels<MarketViewModel>()

    override fun initView() {
        (activity as MainActivity).HideBottomNavigation(true)
        initListener()
        getData()
    }

    private fun getData() {
        marketViewModel.nftData.observe(viewLifecycleOwner) {
            binding.nftItem = it
            if (it!!.isLike) {
                binding.ivBidNftLike.setImageResource(R.drawable.ic_market_like_selected)
            } else {
                binding.ivBidNftLike.setImageResource(R.drawable.ic_market_like)
            }
        }
        marketViewModel.getData(args.nftItem)
    }

    private fun initListener() {
        binding.ivBack.setOnClickListener { popBackStack() }
        binding.ivBidNftLike.setOnClickListener {
            val isLike = args.nftItem.isLike
            if(!isLike) {
                lifecycleScope.launch {
                    val like = marketViewModel.requestBookmark(args.nftItem.auctionId)
                    if (like == "true") {
                        binding.ivBidNftLike.setBackgroundResource(R.drawable.ic_market_like_selected)
                        args.nftItem.isLike = true
                    } else if (like == "false") {
                        binding.ivBidNftLike.setBackgroundResource(R.drawable.ic_market_like)
                        args.nftItem.isLike = false
                    }
                }
            } else {
                lifecycleScope.launch {
                    val like = marketViewModel.cancelBookmark(args.nftItem.auctionId)
                    if (like == "true") {
                        binding.ivBidNftLike.setBackgroundResource(R.drawable.ic_market_like)
                        args.nftItem.isLike = false
                    } else if (like == "false") {
                        binding.ivBidNftLike.setBackgroundResource(R.drawable.ic_market_like_selected)
                        args.nftItem.isLike = true
                    }
                }
            }
        }
        binding.clUpload.setOnClickListener {
        //입찰 진행
        }

    }

}