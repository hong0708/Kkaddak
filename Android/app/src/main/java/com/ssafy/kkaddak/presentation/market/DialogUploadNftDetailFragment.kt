package com.ssafy.kkaddak.presentation.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.kkaddak.ApplicationClass
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.common.util.NFTFunction
import com.ssafy.kkaddak.common.util.WalletFunction
import com.ssafy.kkaddak.databinding.DialogUploadNftDetailBinding
import java.math.BigInteger

class DialogUploadNftDetailFragment : DialogFragment() {

    private var _binding: DialogUploadNftDetailBinding? = null
    private val binding get() = _binding!!
    private val uploadNftadapter by lazy { UploadNftItemAdapter(this::getNftDetail) }
    private val marketViewModel by activityViewModels<MarketViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //false로 설정해 주면 화면밖 혹은 뒤로가기 버튼시 다이얼로그라 dismiss 되지 않는다.
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogUploadNftDetailBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawableResource(R.color.black_transparent80)
        nftinit()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvConfirmNftItem.setOnClickListener {
            marketViewModel.nftId.observe(viewLifecycleOwner) { }
            dialog?.dismiss()
        }
        binding.tvCancelNftItem.setOnClickListener {
            dialog?.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun nftinit() {
        binding.rvSelectNftItem.apply {
            adapter = uploadNftadapter
            layoutManager =
                GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            val spanCount = 2
            val space = 40
            addItemDecoration(GridSpaceItemDecoration(spanCount, space))
        }

        NFTFunction().getTokensOfOwner(getWalletId()).observe(viewLifecycleOwner) { lists ->
            uploadNftadapter.setData(lists)
        }

    }

    private fun getWalletId() : String {
        return String(
            ApplicationClass.keyStore.decryptData(
                WalletFunction().decode(ApplicationClass.preferences.walletAddress.toString())
            )
        )
    }

    private fun getNftDetail(nftId: BigInteger) {
        marketViewModel.setNftId(nftId)
    }
}