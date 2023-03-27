package com.ssafy.kkaddak.presentation.songlist

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.FragmentLikeListBinding
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LikeListFragment : BaseFragment<FragmentLikeListBinding>(R.layout.fragment_like_list) {

    private val likeListAdapter by lazy { LikeListAdapter(this::getSongDetail) }
    private val songViewModel by activityViewModels<SongViewModel>()

    override fun initView() {
        initRecyclerView()
        initListener()
    }

    private fun initListener() {
        binding.ivBack.setOnClickListener { popBackStack() }
    }

    private fun initRecyclerView() {
        binding.rvLikeList.apply {
            adapter = likeListAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
        songViewModel.likeListData.observe(viewLifecycleOwner) { response ->
            response?.let { likeListAdapter.setSong(it) }
        }
        songViewModel.getLikeList()
    }

    private fun getSongDetail(songId: String) {
        navigate(
            LikeListFragmentDirections.actionLikeListFragmentToSongDetailFragment(
                songId
            )
        )
    }
}