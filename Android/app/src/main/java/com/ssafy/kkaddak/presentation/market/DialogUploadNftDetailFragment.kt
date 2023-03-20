package com.ssafy.kkaddak.presentation.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.DialogUploadNftDetailBinding

class DialogUploadNftDetailFragment : DialogFragment() {

    private var _binding: DialogUploadNftDetailBinding? = null
    private val binding get() = _binding!!
    var uploadnftadapter: UploadNftItemAdapter? = null

    //UploadMarketFragment에 데이터 넘겨주기 위한 인터페이스
    interface FragmentInterfacer {
        fun onSendNftInfo(input: UploadNftItem)
    }
    private var fragmentInterfacer: FragmentInterfacer? = null
    fun setFragmentInterfacer(fragmentInterfacer: FragmentInterfacer?) {
        this.fragmentInterfacer = fragmentInterfacer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //false로 설정해 주면 화면밖 혹은 뒤로가기 버튼시 다이얼로그라 dismiss 되지 않는다.
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogUploadNftDetailBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawableResource(R.color.trans80_black)
        nftinit()
        getNft()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvConfirmNftItem.setOnClickListener {
            dialog?.dismiss()
        }
        binding.tvCancelNftItem.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun nftinit() {
        uploadnftadapter = UploadNftItemAdapter()
        binding.rvSelectNftItem.apply {
            adapter = uploadnftadapter
            layoutManager =
                GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            val spanCount = 2
            val space = 40
            addItemDecoration(GridSpaceItemDecoration(spanCount, space))
        }
    }

    // 내가 보유한 Nft
    private fun getNft() {
        var data = UploadNftItem(R.drawable.ic_nft_mockup1, "노래 제목1")
        uploadnftadapter?.addItem(data)
        data = UploadNftItem(R.drawable.ic_nft_mockup2, "노래 제목2")
        uploadnftadapter?.addItem(data)
        data = UploadNftItem(R.drawable.ic_nft_mockup3, "노래 제목3")
        uploadnftadapter?.addItem(data)
        data = UploadNftItem(R.drawable.ic_nft_mockup4, "노래 제목4")
        uploadnftadapter?.addItem(data)
        data = UploadNftItem(R.drawable.ic_nft_mockup1, "노래 제목1")
        uploadnftadapter?.addItem(data)
        data = UploadNftItem(R.drawable.ic_nft_mockup2, "노래 제목2")
        uploadnftadapter?.addItem(data)
        data = UploadNftItem(R.drawable.ic_nft_mockup3, "노래 제목3")
        uploadnftadapter?.addItem(data)
        data = UploadNftItem(R.drawable.ic_nft_mockup4, "노래 제목4")
        uploadnftadapter?.addItem(data)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}