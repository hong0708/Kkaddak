package com.ssafy.kkaddak.presentation.market

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.common.util.BindingAdapters.setNormalImg
import com.ssafy.kkaddak.common.util.BindingAdapters.setProfileImg
import com.ssafy.kkaddak.databinding.FragmentBuyMarketBinding
import com.ssafy.kkaddak.presentation.MainActivity
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BuyMarketFragment :
    BaseFragment<FragmentBuyMarketBinding>(R.layout.fragment_buy_market) {

    private val args by navArgs<BuyMarketFragmentArgs>()
    private val marketViewModel by activityViewModels<MarketViewModel>()
    private lateinit var historyadapter: HistoryAdapter
    override fun initView() {
        (activity as MainActivity).hideBottomNavigation(true)
        initListener()
        initRecyclerView()
        getData()
        setData()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).hideBottomNavigation(false)
    }

    private fun getData() {
        marketViewModel.nftData.observe(viewLifecycleOwner) {
            binding.nftItem = it
            if (it!!.isLike) {
                binding.ivNftLike.setImageResource(R.drawable.ic_market_like_selected)
            } else {
                binding.ivNftLike.setImageResource(R.drawable.ic_market_like_nft_detail)
            }
        }
        marketViewModel.getData(args.nftItem)
        marketViewModel.getCreatorImg(args.nftItem.nftCreator)
        marketViewModel.tempHistory()
    }

    private fun setData() {
        binding.apply {
            ivNftImage.setNormalImg(args.nftItem.nftImagePath)
            tvContentSellingEth.text = args.nftItem.nftPrice.toString()
            ivNftCreatorProfile.setProfileImg(marketViewModel.creatorImg)
        }
    }

    private fun initRecyclerView() {
        historyadapter = HistoryAdapter()
        binding.rvHistory.apply {
            adapter = historyadapter
            layoutManager = LinearLayoutManager(context)
        }

        marketViewModel.nftHistoryData.observe(viewLifecycleOwner) { response ->
            response?.let {
                historyadapter.setDatas(it)
                if (historyadapter.itemCount == 0) {
                    binding.tvEmptyHistory.visibility = View.VISIBLE
                } else {
                    binding.tvEmptyHistory.visibility = View.GONE
                }
            }
        }
    }

    private fun initListener() {
        binding.ivBack.setOnClickListener { popBackStack() }
        binding.ivNftLike.setOnClickListener {
            val isLike = args.nftItem.isLike
            if (!isLike) {
                lifecycleScope.launch {
                    val like = marketViewModel.requestBookmark(args.nftItem.marketId)
                    if (like == "true") {
                        binding.ivNftLike.setBackgroundResource(R.drawable.ic_market_like_selected)
                        args.nftItem.isLike = true
                    } else if (like == "false") {
                        binding.ivNftLike.setBackgroundResource(R.drawable.ic_market_like_nft_detail)
                        args.nftItem.isLike = false
                    }
                }
            } else {
                lifecycleScope.launch {
                    val like = marketViewModel.cancelBookmark(args.nftItem.marketId)
                    if (like == "true") {
                        binding.ivNftLike.setBackgroundResource(R.drawable.ic_market_like_nft_detail)
                        args.nftItem.isLike = false
                    } else if (like == "false") {
                        binding.ivNftLike.setBackgroundResource(R.drawable.ic_market_like_selected)
                        args.nftItem.isLike = true
                    }
                }
            }
        }
        binding.clBuy.setOnClickListener {
            navigate(
                BuyMarketFragmentDirections.actionBuyMarketFragmentToBuyFragment(
                    args.nftItem.nftImagePath,
                    args.nftItem.nftCreator,
                    args.nftItem.nftPrice.toString()
                )
            )
        }
    }

    override fun navigateToProfile(creatorId: String) {
        navigate(BuyMarketFragmentDirections.actionBuyMarketFragmentToOtherProfileFragment(creatorId))
    }
}