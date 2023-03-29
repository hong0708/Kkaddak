package com.ssafy.kkaddak.presentation.songlist

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.FragmentSongDetailBinding
import com.ssafy.kkaddak.presentation.MainActivity
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SongDetailFragment :
    BaseFragment<FragmentSongDetailBinding>(R.layout.fragment_song_detail) {

    private val args by navArgs<SongDetailFragmentArgs>()
    private val songViewModel by activityViewModels<SongViewModel>()

    private var player: ExoPlayer? = null

    override fun initView() {
        (activity as MainActivity).hideBottomNavigation(true)
        initListener()
        getData()
        initPlayer()
    }

    private fun initListener() {
        binding.btnBack.setOnClickListener { popBackStack() }
        binding.ivSongList.setOnClickListener {
            navigate(SongDetailFragmentDirections.actionSongDetailFragmentToSongListFragment())
        }
        binding.ivFavorite.setOnClickListener {
            lifecycleScope.launch {
                val like = songViewModel.requestBookmark(args.songId)
                if (like == "true")
                    binding.ivFavorite.setBackgroundResource(R.drawable.ic_song_detail_favorite_selected)
                else if (like == "false")
                    binding.ivFavorite.setBackgroundResource(R.drawable.ic_song_detail_favorite)
            }
        }
        binding.tvSongCreator.setOnClickListener {
            navigate(
                SongDetailFragmentDirections.actionSongDetailFragmentToOtherProfileFragment(
                    songViewModel.songData.value?.nickname.toString()
                )
            )
        }
    }

    private fun getData() {
        songViewModel.songData.observe(viewLifecycleOwner) {
            binding.songDetail = it
            if (it!!.like!!)
                binding.ivFavorite.setBackgroundResource(R.drawable.ic_song_detail_favorite_selected)
            else
                binding.ivFavorite.setBackgroundResource(R.drawable.ic_song_detail_favorite)

            if (it != null) {
                buildMediaSource(it.songPath!!).let {
                    player?.prepare(it)
                }
            }
        }
        songViewModel.getSong(args.songId)
    }

    private fun initPlayer() {
        player = ExoPlayer.Builder(requireContext()).build()
        binding.playerControlView.player = player
//        buildMediaSource().let {
//            player?.prepare(it)
//        }
        val mediaItems = ArrayList<MediaItem>()
//        makePlayList(mediaItems)

//        player?.setMediaItems(mediaItems)
//        player?.prepare()
//        player?.playWhenReady = true
    }

    // 영상에 출력할 미디어 정보를 가져오는 클래스
    private fun buildMediaSource(songPath: String): MediaSource {
        val dataSourceFactory = DefaultDataSourceFactory(requireContext(), "sample")
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(songPath))
    }

    // 일시중지
    override fun onResume() {
        super.onResume()
        player?.playWhenReady = true
    }

    override fun onStop() {
        super.onStop()
        player?.stop()
        player?.playWhenReady = false
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        (activity as MainActivity).hideBottomNavigation(false)
    }
}