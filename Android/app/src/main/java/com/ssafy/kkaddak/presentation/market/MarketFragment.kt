package com.ssafy.kkaddak.presentation.market

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
        marketViewModel.getAllNfts(-1, 20)
    }
}