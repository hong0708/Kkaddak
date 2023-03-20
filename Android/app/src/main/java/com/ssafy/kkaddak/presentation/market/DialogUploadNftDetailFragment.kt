package com.ssafy.kkaddak.presentation.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.DialogUploadNftDetailBinding

class DialogUploadNftDetailFragment : DialogFragment() {

    private var _binding: DialogUploadNftDetailBinding? = null
    private val binding get() = _binding!!
    var nftadapter: NftItemAdapter? = null

    //UploadMarketFragment에 데이터 넘겨주기 위한 인터페이스
    interface FragmentInterfacer {
        fun onButtonClick(input: String)
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
        val recyclerView = view?.findViewById<RecyclerView>(R.id.rv_market_nft_list)

        nftadapter = NftItemAdapter()
        recyclerView?.adapter = nftadapter
        recyclerView?.run {
            val spanCount = 2
            val space = 40
            addItemDecoration(GridSpaceItemDecoration(spanCount, space))
        }
    }

    // 내가 보유한 Nft
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}