package com.ssafy.kkaddak.presentation.songlist

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.FragmentPlaylistBinding
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayListFragment :
    BaseFragment<FragmentPlaylistBinding>(R.layout.fragment_playlist) {

    private val songAdapter by lazy { PlayListAdapter(this::getSongDetail, this::deleteSong) }
    private val songViewModel by activityViewModels<SongViewModel>()

    override fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvPlaylist.apply {
            adapter = songAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
        songViewModel.playListData.observe(viewLifecycleOwner) { response ->
            response?.let { songAdapter.setSong(it) }
        }
        songViewModel.getPlayList()
    }

    private fun getSongDetail(songId: String) {
        navigate(
            PlayListFragmentDirections.actionPlayListFragmentToSongDetailFragment(
                songId
            )
        )
    }

    private fun deleteSong(songId: String) {
        songViewModel.deletePlayList(songId)
        songViewModel.getPlayList()
    }
}