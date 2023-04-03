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
    private lateinit var nftId: BigInteger
    private var price: String = ""
    private var uploadNftItem: UploadNftItem? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).hideBottomNavigation(true)
        initView()
        binding.ivBack.setOnClickListener { popBackStack() }
    }

    override fun initView() {
        initListener()
        getData()
        setData()

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
//            val price = binding.etUploadSellingEth.text.toString()

            marketViewModel.nftUploadData.observe(viewLifecycleOwner){  }
            NFTFunction().getMetaData(nftId).observe(viewLifecycleOwner) {
                Log.d("test6", price)
                Log.d("test7", nftId.toString())
                Log.d("ㅅㅂ", it.creatorNickname.toString())
                marketViewModel.uploadNft(nftId.toString(), price.toDouble(), it)
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

                    etUploadSellingEth.addTextChangedListener {
                        price = binding.etUploadSellingEth.text.toString()
                    }
                }
//                marketViewModel.getUploadData(UploadNftItem(it.toString(), nftItem.nftImageUrl,
//                    nftItem.creatorNickname,
//                    nftItem.trackTitle,
//                    price.toDouble()))
            }
        }
    }

    private fun setData() {

//        binding.etUploadSellingEth.addTextChangedListener {
//            val price = binding.etUploadSellingEth.text
//            marketViewModel.setUploadData(nftId.toString(), price.toString(), temp).
////            marketViewModel.nftUploadData.value?.nftPrice = binding.etUploadSellingEth.text.toString().toDouble()
//        }
    }
}