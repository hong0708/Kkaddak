package com.ssafy.kkaddak.presentation.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.DialogUploadNftDetailBinding
import com.ssafy.kkaddak.domain.entity.market.UploadNftItem

class DialogUploadNftDetailFragment : DialogFragment() {

    private var _binding: DialogUploadNftDetailBinding? = null
    private val binding get() = _binding!!
    private var uploadnftadapter: UploadNftItemAdapter? = null
    private var fragmentInterfacer: FragmentInterfacer? = null

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

    //UploadMarketFragment에 데이터 넘겨주기 위한 인터페이스
    interface FragmentInterfacer {
        fun onSendNftInfo(input: UploadNftItem)
    }

    fun setFragmentInterfacer(fragmentInterfacer: FragmentInterfacer?) {
        this.fragmentInterfacer = fragmentInterfacer
    }
}