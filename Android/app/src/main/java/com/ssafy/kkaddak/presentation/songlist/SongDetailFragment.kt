package com.ssafy.kkaddak.presentation.songlist

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.FragmentSongDetailBinding
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SongDetailFragment:
    BaseFragment<FragmentSongDetailBinding>(R.layout.fragment_song_detail) {

    private val args by navArgs<SongDetailFragmentArgs>()
    private val songViewModel by activityViewModels<SongViewModel>()

    override fun initView() {
        initListener()
        getData()
    }

    private fun initListener() {
        binding.btnBack.setOnClickListener { popBackStack() }
        binding.ivSongList.setOnClickListener {
            navigate(SongDetailFragmentDirections.actionSongDetailFragmentToSongListFragment())
        }
    }

    private fun getData() {
        songViewModel.songData.observe(viewLifecycleOwner) {
            binding.songDetail = it
        }
        songViewModel.getSong(args.songId)
    }
}