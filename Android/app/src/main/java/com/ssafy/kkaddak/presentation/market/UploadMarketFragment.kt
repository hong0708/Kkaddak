package com.ssafy.kkaddak.presentation.market

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.common.util.BindingAdapters.setNormalImg
import com.ssafy.kkaddak.common.util.NFTFunction
import com.ssafy.kkaddak.databinding.FragmentUploadMarketBinding
import com.ssafy.kkaddak.presentation.MainActivity
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.math.BigInteger

@AndroidEntryPoint
class UploadMarketFragment :
    BaseFragment<FragmentUploadMarketBinding>(R.layout.fragment_upload_market) {

    private val marketViewModel by activityViewModels<MarketViewModel>()
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
        marketViewModel.clearNftId()
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
            marketViewModel.nftId.observe(viewLifecycleOwner) { nftId ->
                nftId?.let { tokenId ->
                    NFTFunction().getMetaData(tokenId).observe(viewLifecycleOwner) {
                        try {
                            NFTFunction().sellMusicNFT(
                                tokenId,
                                BigInteger(price).multiply(BigInteger.valueOf(100000000))
                            )
                            try {
                                marketViewModel.uploadNft(tokenId.toString(), price.toDouble(), it)
                                showToast("판매 등록이 완료되었습니다.")
                                navigate(UploadMarketFragmentDirections.actionUploadMarketFragmentToMarketFragment())
                            } catch (e: Exception) {
                                e.printStackTrace()
                                showToast("판매 등록에 실패하였습니다.")
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            showToast("판매 등록에 실패하였습니다.")
                        }
                    }
                }
            }
        }
    }

    private fun getData() {
        marketViewModel.nftId.observe(viewLifecycleOwner) {
            it?.let { tokenId ->
                NFTFunction().getMetaData(tokenId).observe(viewLifecycleOwner) { nftItem ->
                    binding.apply {
                        tvUploadSongTitle.text = nftItem.trackTitle
                        ivUploadNftImage.setNormalImg(nftItem.nftImageUrl!!.toUri())
                        etUploadSellingKat.addTextChangedListener {
                            price = etUploadSellingKat.text.toString()
                        }
                    }
                }
            }
        }
    }
}