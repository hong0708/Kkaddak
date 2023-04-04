package com.ssafy.kkaddak.presentation.market

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.net.toUri
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.common.util.BindingAdapters.setNormalImg
import com.ssafy.kkaddak.common.util.NFTFunction
import com.ssafy.kkaddak.databinding.FragmentUploadMarketBinding
import com.ssafy.kkaddak.domain.entity.market.UploadNftItem
import com.ssafy.kkaddak.presentation.MainActivity
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.math.BigInteger

@AndroidEntryPoint
class UploadMarketFragment :
    BaseFragment<FragmentUploadMarketBinding>(R.layout.fragment_upload_market) {

    private val marketViewModel by activityViewModels<MarketViewModel>()
    private var uploadNftItem: UploadNftItem? = null
    private lateinit var nftId: BigInteger
    private var price: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).hideBottomNavigation(true)
        initView()
        binding.ivBack.setOnClickListener { popBackStack() }
    }

    override fun initView() {
        initListener()
        getData()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).hideBottomNavigation(false)
    }

    override fun navigateToProfile(creatorId: String) {
        UploadMarketFragmentDirections.actionUploadMarketFragmentToOtherProfileFragment(creatorId)
    }

    private fun initListener() {
        binding.tvSelectNftImage.setOnClickListener {
            val dialog = DialogUploadNftDetailFragment()
            dialog.show(requireActivity().supportFragmentManager, "DialogUploadNftDetailFragment")
        }
        binding.clUpload.setOnClickListener {
            NFTFunction().getMetaData(nftId).observe(viewLifecycleOwner) {
                try {
                    NFTFunction().sellMusicNFT(
                        nftId,
                        BigInteger(price).multiply(BigInteger.valueOf(100000000))
                    )
                    try {
                        marketViewModel.uploadNft(nftId.toString(), price.toDouble(), it)
                        showToast("판매 등록이 완료되었습니다.")
                        navigate(UploadMarketFragmentDirections.actionUploadMarketFragmentToMarketFragment())
                    } catch (e: Exception) {
                        Log.e("upload error", e.toString())
                        showToast("판매 등록에 실패하였습니다.")
                    }
                } catch (e: Exception) {
                    Log.e("nft contract error", e.toString())
                    showToast("판매 등록에 실패하였습니다.")
                }
            }
        }
    }

    private fun getData() {
        marketViewModel.nftId.observe(viewLifecycleOwner) {
            nftId = it
            uploadNftItem?.nftId = it.toString()
            NFTFunction().getMetaData(it).observe(viewLifecycleOwner) { nftItem ->
                binding.apply {
                    tvUploadSongTitle.text = nftItem.trackTitle
                    ivUploadNftImage.setNormalImg(nftItem.nftImageUrl!!.toUri())

                    etUploadSellingKat.addTextChangedListener {
                        price = binding.etUploadSellingKat.text.toString()
                    }
                }
            }
        }
    }

}