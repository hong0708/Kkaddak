package com.ssafy.kkaddak.presentation.market


import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.FragmentMarketBinding
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarketFragment : BaseFragment<FragmentMarketBinding>(R.layout.fragment_market) {

    var nftadapter: NftItemAdapter? = null

    override fun initView() {
        nftinit()
        getNft()
    }

    private fun nftinit() {
//        val recyclerView = view?.findViewById<RecyclerView>(R.id.rv_market_nft_list)
//
//        nftadapter = NftItemAdapter()
//        recyclerView?.adapter = nftadapter
//        recyclerView?.run {
//            val spanCount = 2
//            val space = 20
//            addItemDecoration(GridSpaceItemDecoration(spanCount, space))
//        }
    }
    private fun getNft() {
        var data = NftItem(R.drawable.ic_nft_mockup1, 1, "창작자1", "노래 제목1", "23.03.10", 1.23)
        nftadapter?.addItem(data)
        data = NftItem(R.drawable.ic_nft_mockup2, 2, "창작자2", "노래 제목2", "23.03.10", 1.24)
        nftadapter?.addItem(data)
        data = NftItem(R.drawable.ic_nft_mockup3, 3, "창작자3", "노래 제목3", "23.03.10", 5.23)
        nftadapter?.addItem(data)
        data = NftItem(R.drawable.ic_nft_mockup4, 4, "창작자4", "노래 제목4", "23.03.10", 8.23)
        nftadapter?.addItem(data)
        data = NftItem(R.drawable.ic_nft_mockup1, 1, "창작자1", "노래 제목1", "23.03.10", 1.23)
        nftadapter?.addItem(data)
        data = NftItem(R.drawable.ic_nft_mockup2, 2, "창작자2", "노래 제목2", "23.03.10", 1.24)
        nftadapter?.addItem(data)
        data = NftItem(R.drawable.ic_nft_mockup3, 3, "창작자3", "노래 제목3", "23.03.10", 5.23)
        nftadapter?.addItem(data)
        data = NftItem(R.drawable.ic_nft_mockup4, 4, "창작자4", "노래 제목4", "23.03.10", 8.23)
        nftadapter?.addItem(data)
    }
}