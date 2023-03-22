package com.ssafy.kkaddak.presentation.songlist

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.FragmentSongListBinding
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SongListFragment :
    BaseFragment<FragmentSongListBinding>(R.layout.fragment_song_list) {

    private val songAdapter by lazy { SongAdapter(this::getSongDetail) }
    private val songViewModel by activityViewModels<SongViewModel>()

    override fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvSongList.apply {
            adapter = songAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
        songViewModel.songListData.observe(viewLifecycleOwner) { response ->
            response?.let { songAdapter.setSong(it) }
        }
        songViewModel.getSongs()
    }

    private fun getSongDetail(songId: String) {
        navigate(
            SongListFragmentDirections.actionSongListFragmentToSongDetailFragment(
                songId
            )
        )
    }
}