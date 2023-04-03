package com.ssafy.kkaddak.presentation.profile

import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.common.util.NFTFunction
import com.ssafy.kkaddak.databinding.FragmentProfileNftBinding
import com.ssafy.kkaddak.presentation.base.BaseFragment
import com.ssafy.kkaddak.presentation.market.GridSpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import java.math.BigInteger

@AndroidEntryPoint
class ProfileNFTFragment :
    BaseFragment<FragmentProfileNftBinding>(R.layout.fragment_profile_nft) {

    private val args by navArgs<ProfileNFTFragmentArgs>()
    private val profileNFTAdapter by lazy {
        ProfileNFTAdapter(
            args.isMine,
            this::getNFTDetail,

        )
    }

    override fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvProfileNft.apply {
            adapter = profileNFTAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
            addItemDecoration(GridSpaceItemDecoration(3, 3))
        }

        NFTFunction().getTokensOfOwner(args.walletId).observe(viewLifecycleOwner) { lists ->
            profileNFTAdapter.setData(lists)
        }
    }

    private fun getNFTDetail(nftId: BigInteger, isMine: Boolean) {
        NFTFunction().getMetaData(nftId).observe(viewLifecycleOwner) { nftItem ->
            NFTDetailDialog(requireActivity(), nftItem, isMine).show()
        }
    }

    override fun navigateToProfile(creatorId: String) {}
}