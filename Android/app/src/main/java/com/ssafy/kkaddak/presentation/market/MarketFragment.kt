package com.ssafy.kkaddak.presentation.market

import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.FragmentMarketBinding
import com.ssafy.kkaddak.domain.entity.market.NftItem
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MarketFragment : BaseFragment<FragmentMarketBinding>(R.layout.fragment_market) {

    private val marketViewModel by activityViewModels<MarketViewModel>()
    private val nftadapter by lazy { NftItemAdapter(this::getNftItem) }
    private var isLoading = false

    override fun initView() {
        initRecyclerView()

        binding.ivUpload.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host, UploadMarketFragment())
                .addToBackStack(null)
                .commit()
        }
        binding.tvAllNfts.setOnClickListener { onButtonClick(binding.tvAllNfts) }
        binding.tvSellingNft.setOnClickListener { onButtonClick(binding.tvSellingNft) }
        binding.tvBookmarkNft.setOnClickListener { onButtonClick(binding.tvBookmarkNft) }
    }

    private fun initRecyclerView() {
        marketViewModel.clearData()
        marketViewModel.getAllNfts(-1, 20, false)

        binding.rvMarketNftList.apply {
            adapter = nftadapter
            layoutManager =
                GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            val spanCount = 2
            val space = 20
            addItemDecoration(GridSpaceItemDecoration(spanCount, space))
        }

        marketViewModel.nftTempData.observe(viewLifecycleOwner) { marketViewModel.getSum() }

        marketViewModel.nftListData.observe(viewLifecycleOwner) { response -> response?.let { nftadapter.setNfts(it) } }

        binding.rvMarketNftList.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = (recyclerView.layoutManager as GridLayoutManager?)!!.findLastCompletelyVisibleItemPosition() // 화면에 보이는 마지막 아이템의 position
                val itemTotalCount = recyclerView.adapter!!.itemCount - 1 // 어댑터에 등록된 아이템의 총 개수 -1

                // 스크롤이 최하단에 도착했을 때
                if(!isLoading && !binding.rvMarketNftList.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                    isLoading = true
                    addData(false)
                }
            }
        })
    }

    private fun allRecyclerView() {
        marketViewModel.clearData()
        marketViewModel.getAllNfts(-1, 20, false)

        binding.rvMarketNftList.apply {
            adapter = nftadapter
            layoutManager =
                GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }
        marketViewModel.nftListData.observe(viewLifecycleOwner) { response ->
            response?.let { nftadapter.setNfts(it) }
        }
        marketViewModel.getAllNfts(-1, 20, false)
    }

    fun onButtonClick(tv: TextView) {
        when (tv) {
            binding.tvAllNfts -> {
                tv.setBackgroundResource(R.drawable.bg_rect_bitter_sweet_to_neon_pink_bastille_radius50_stroke2)
                binding.apply {
                    tvSellingNft.setBackgroundResource(R.drawable.bg_rect_bastille_radius50)
                    tvBookmarkNft.setBackgroundResource(R.drawable.bg_rect_bastille_radius50)
                }
            }
            binding.tvSellingNft -> {
                tv.setBackgroundResource(R.drawable.bg_rect_bitter_sweet_to_neon_pink_bastille_radius50_stroke2)
                binding.apply {
                    tvAllNfts.setBackgroundResource(R.drawable.bg_rect_bastille_radius50)
                    tvBookmarkNft.setBackgroundResource(R.drawable.bg_rect_bastille_radius50)
                }
            }
            binding.tvBookmarkNft -> {
                tv.setBackgroundResource(R.drawable.bg_rect_bitter_sweet_to_neon_pink_bastille_radius50_stroke2)
                binding.apply {
                    tvAllNfts.setBackgroundResource(R.drawable.bg_rect_bastille_radius50)
                    tvSellingNft.setBackgroundResource(R.drawable.bg_rect_bastille_radius50)
                }
            }
        }
    }
}