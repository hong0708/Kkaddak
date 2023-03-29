package com.ssafy.kkaddak.presentation.songlist

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ClippingMediaSource
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.FragmentSongDetailBinding
import com.ssafy.kkaddak.domain.entity.song.SongItem
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
    private val concatenatingMediaSource = ConcatenatingMediaSource()
    private val mediaSourceList = mutableListOf<MediaSource>()

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
        }
        songViewModel.getSong(args.songId)

        val startPositionMs = 0L
        val endPositionMs = 60_000L
        val dataSourceFactory = DefaultDataSourceFactory(requireContext(), "sample")
        songViewModel.playListData.observe(viewLifecycleOwner) {
            it?.forEach { musicItem ->
                val mediaItem = MediaItem.Builder()
                    .setUri(musicItem.songPath)
                    .setMediaId(musicItem.songId)
                    .setMediaMetadata(
                        MediaMetadata.Builder()
                            .setTitle(musicItem.songTitle)
                            .setArtist(musicItem.nickname)
                            .setAlbumTitle(musicItem.coverPath)
                            .setDescription(musicItem.isSubscribe.toString())
                            .build()
                    )
                    .build()

                val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(mediaItem)
                if (musicItem.isSubscribe) {
                    mediaSourceList.add(mediaSource)
                } else {
                    mediaSourceList.add(
                        ClippingMediaSource(
                            mediaSource,
                            startPositionMs * 1000L,
                            endPositionMs * 1000L
                        )
                    )
                }
            }
            concatenatingMediaSource.addMediaSources(mediaSourceList)
            player?.prepare(concatenatingMediaSource)
            player?.playWhenReady = true
        }
        songViewModel.getPlayList()
    }

    private fun initPlayer() {
        player = ExoPlayer.Builder(requireContext()).build()
        binding.playerControlView.player = player
        player?.addListener(object : Player.Listener {
            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                super.onMediaItemTransition(mediaItem, reason)
                mediaItem?.apply {
                    songViewModel.songData.postValue(
                        SongItem(
                            mediaId,
                            mediaMetadata.title.toString(),
                            "",
                            mediaMetadata.albumTitle.toString(),
                            "",
                            null,
                            mediaMetadata.artist.toString(),
                            true,
                            null,
                            "",
                            mediaMetadata.description.toString() == "true"
                        )
                    )
                }
            }
        })
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