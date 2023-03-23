package com.ssafy.kkaddak.presentation.market

import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.FragmentMarketBinding
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MarketFragment : BaseFragment<FragmentMarketBinding>(R.layout.fragment_market) {

    private var nftadapter: NftItemAdapter? = null
    private val marketViewModel by activityViewModels<MarketViewModel>()

    override fun initView() {
        initRecyclerView()

        binding.ivUpload.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host, UploadMarketFragment())
                .addToBackStack(null)
                .commit()
        }
        binding.tvAllNfts.setOnClickListener { onButtonClick(binding.tvAllNfts) }
        binding.tvSellingNft.setOnClickListener {
            onButtonClick(binding.tvSellingNft)

        }
        binding.tvBiddingNft.setOnClickListener { onButtonClick(binding.tvBiddingNft) }
        binding.tvBookmarkNft.setOnClickListener { onButtonClick(binding.tvBookmarkNft) }
    }

    private fun initRecyclerView() {
        nftadapter = NftItemAdapter()
        binding.rvMarketNftList.apply {
            adapter = nftadapter
            layoutManager =
                GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            val spanCount = 2
            val space = 20
            addItemDecoration(GridSpaceItemDecoration(spanCount, space))

        }
        marketViewModel.nftListData.observe(viewLifecycleOwner) { response ->
            response?.let { nftadapter!!.setNfts(it) }
        }
        marketViewModel.getAllNfts(-1, 20, false)
    }

    fun onButtonClick(tv: TextView) {
        when (tv) {
            binding.tvAllNfts -> {
                tv.setBackgroundResource(R.drawable.bg_rect_bitter_sweet_to_neon_pink_bastille_radius50_stroke2)
                binding.apply {
                    tvSellingNft.setBackgroundResource(R.drawable.bg_rect_bastille_radius50)
                    tvBiddingNft.setBackgroundResource(R.drawable.bg_rect_bastille_radius50)
                    tvBookmarkNft.setBackgroundResource(R.drawable.bg_rect_bastille_radius50)
                }
            }
            binding.tvSellingNft -> {
                tv.setBackgroundResource(R.drawable.bg_rect_bitter_sweet_to_neon_pink_bastille_radius50_stroke2)
                binding.apply {
                    tvAllNfts.setBackgroundResource(R.drawable.bg_rect_bastille_radius50)
                    tvBiddingNft.setBackgroundResource(R.drawable.bg_rect_bastille_radius50)
                    tvBookmarkNft.setBackgroundResource(R.drawable.bg_rect_bastille_radius50)
                }
            }
            binding.tvBiddingNft -> {
                tv.setBackgroundResource(R.drawable.bg_rect_bitter_sweet_to_neon_pink_bastille_radius50_stroke2)
                binding.apply {
                    tvAllNfts.setBackgroundResource(R.drawable.bg_rect_bastille_radius50)
                    tvSellingNft.setBackgroundResource(R.drawable.bg_rect_bastille_radius50)
                    tvBookmarkNft.setBackgroundResource(R.drawable.bg_rect_bastille_radius50)
                }

            }
            binding.tvBookmarkNft -> {
                tv.setBackgroundResource(R.drawable.bg_rect_bitter_sweet_to_neon_pink_bastille_radius50_stroke2)
                binding.apply {
                    tvAllNfts.setBackgroundResource(R.drawable.bg_rect_bastille_radius50)
                    tvSellingNft.setBackgroundResource(R.drawable.bg_rect_bastille_radius50)
                    tvBiddingNft.setBackgroundResource(R.drawable.bg_rect_bastille_radius50)
                }
            }
        }
    }
}