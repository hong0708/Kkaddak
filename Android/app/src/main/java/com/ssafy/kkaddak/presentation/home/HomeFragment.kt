package com.ssafy.kkaddak.presentation.home

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.FragmentHomeBinding
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val latestSongAdapter by lazy { LatestSongAdapter(this::getSongDetail) }
    private val homeViewModel by activityViewModels<HomeViewModel>()

    override fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvNewSong.apply {
            adapter = latestSongAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }
        homeViewModel.latestSongsList.observe(viewLifecycleOwner) { response ->
            response?.let { latestSongAdapter.setSong(it) }
        }
        homeViewModel.getLatestSongs()
    }

    private fun getSongDetail(songId: String) {
        navigate(
            HomeFragmentDirections.actionHomeFragmentToSongDetailFragment(
                songId
            )
        )
    }
}