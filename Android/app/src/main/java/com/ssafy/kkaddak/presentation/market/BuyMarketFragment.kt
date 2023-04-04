package com.ssafy.kkaddak.presentation.market

import android.view.View
import androidx.core.net.toUri
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
        marketViewModel.nftDetailData.observe(viewLifecycleOwner) {
            binding.nftDetailItem = it
            if (it!!.isLike) {
                binding.ivNftLike.setImageResource(R.drawable.ic_market_like_selected)
            } else {
                binding.ivNftLike.setImageResource(R.drawable.ic_market_like_nft_detail)
            }
        }
        marketViewModel.getNftDetail(args.marketId)
        marketViewModel.nftDetailData.value?.let { marketViewModel.getCreatorImg(it.sellerNickname) }
    }

    private fun setData() {
        marketViewModel.nftDetailData.observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    ivNftImage.setNormalImg(it.nftImageUrl.toUri())
                    tvContentSellingEth.text = String.format("%.1f", it.price)
                }
            }
        }
        binding.ivNftCreatorProfile.setProfileImg(marketViewModel.creatorImg)

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
        binding.apply {
            ivBack.setOnClickListener { popBackStack() }
            ivNftLike.setOnClickListener {
                val isLike = marketViewModel.nftDetailData.value!!.isLike
                if (!isLike) {
                    lifecycleScope.launch {
                        val like = marketViewModel.requestBookmark(args.marketId)
                        if (like == "true") {
                            ivNftLike.setBackgroundResource(R.drawable.ic_market_like_selected)
                            marketViewModel.nftDetailData.value!!.isLike = true
                        } else if (like == "false") {
                            ivNftLike.setBackgroundResource(R.drawable.ic_market_like_nft_detail)
                            marketViewModel.nftDetailData.value!!.isLike = false
                        }
                    }
                } else {
                    lifecycleScope.launch {
                        val like = marketViewModel.cancelBookmark(args.marketId)
                        if (like == "true") {
                            ivNftLike.setBackgroundResource(R.drawable.ic_market_like_nft_detail)
                            marketViewModel.nftDetailData.value!!.isLike = false
                        } else if (like == "false") {
                            ivNftLike.setBackgroundResource(R.drawable.ic_market_like_selected)
                            marketViewModel.nftDetailData.value!!.isLike = true
                        }
                    }
                }
            }
            clBuy.setOnClickListener {
                navigate(
                    BuyMarketFragmentDirections.actionBuyMarketFragmentToBuyFragment(
                        marketViewModel.nftDetailData.value!!.nftImageUrl,
                        marketViewModel.nftDetailData.value!!.creatorNickname,
                        marketViewModel.nftDetailData.value!!.price.toString()
                    )
                )
            }
        }
    }

    override fun navigateToProfile(creatorId: String) {
        navigate(
            BuyMarketFragmentDirections.actionBuyMarketFragmentToOtherProfileFragment(
                creatorId
            )
        )
    }
}