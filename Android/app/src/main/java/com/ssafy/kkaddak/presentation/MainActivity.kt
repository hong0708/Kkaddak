package com.ssafy.kkaddak.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ClippingMediaSource
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.ActivityMainBinding
import com.ssafy.kkaddak.domain.entity.song.SongItem
import com.ssafy.kkaddak.presentation.base.BaseFragment
import com.ssafy.kkaddak.presentation.songlist.SongViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var behavior: BottomSheetBehavior<View>
    private lateinit var navHostFragment: NavHostFragment

    private val songViewModel by viewModels<SongViewModel>()
    private val concatenatingMediaSource = ConcatenatingMediaSource()
    private val songList = mutableListOf<SongItem>()

    // private val mediaSourceList = mutableListOf<MediaSource>()
    private var player: ExoPlayer? = null
    private var songId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeData()
        initBottomBehavior()
        initNavigation()
        initListener()

    }

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
        player?.stop()
        player?.release()
    }

    private fun observeData() {
        songViewModel.playListData.observe(this) { playList ->

            songList.clear()

            playList?.forEach { it ->
                songList.add(it)
            }
        }

        songViewModel.songData.observe(this) {
            binding.songDetail = it
            if (it!!.like!!)
                binding.ivFavorite.setBackgroundResource(R.drawable.ic_song_detail_favorite_selected)
            else
                binding.ivFavorite.setBackgroundResource(R.drawable.ic_song_detail_favorite)
        }
    }

    private fun initListener() {
        binding.apply {
            ivDelete.setOnClickListener {
                behavior.state = BottomSheetBehavior.STATE_HIDDEN
                player?.stop()
                player?.release()
                hideBottomNavigation(false)
                concatenatingMediaSource.clear()
            }
            ivFavorite.setOnClickListener {
                lifecycleScope.launch {
                    val like = songViewModel.requestBookmark(songId)
                    if (like == "true")
                        binding.ivFavorite.setBackgroundResource(R.drawable.ic_song_detail_favorite_selected)
                    else if (like == "false")
                        binding.ivFavorite.setBackgroundResource(R.drawable.ic_song_detail_favorite)
                }
            }
            tvSongCreator.setOnClickListener {
                behavior.state = BottomSheetBehavior.STATE_HIDDEN
                val currentFragment =
                    navHostFragment.childFragmentManager.fragments[0] as BaseFragment<*>
                currentFragment.navigateToProfile(songViewModel.songData.value?.nickname.toString())
                player?.stop()
                player?.release()
                concatenatingMediaSource.clear()
            }
        }
    }

    private fun initBottomBehavior() {
        behavior = BottomSheetBehavior.from(binding.clBottomSheet)
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    binding.clBottomSheetExpanded.visibility = View.GONE
                    binding.clBottomSheetCollapsed.visibility = View.VISIBLE
                    hideBottomNavigation(false)
                }
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    binding.apply {
                        clBottomSheetExpanded.visibility = View.VISIBLE
                        clBottomSheetCollapsed.visibility = View.GONE
                    }
                    hideBottomNavigation(true)
                }
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    player?.stop()
                    player?.release()
                    concatenatingMediaSource.clear()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
    }

    private fun initNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController

        val graphInflater = navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.navigation_main)

        binding.bottomNavigation.apply {
            setupWithNavController(navController)
            itemIconTintList = null
            selectedItemId = R.id.blank
        }
        binding.fabHome.setOnClickListener {
            navController.navigate(R.id.homeFragment)
        }
        navController.graph = navGraph
    }

    fun setSongDetail(songId: String) {
        this.songId = songId
        runBlocking {
            songViewModel.getSong(this@MainActivity.songId)
        }
        runBlocking {
            songViewModel.getPlayList()
        }
        setPlay()
    }

    private fun initPlayer() {
        player = ExoPlayer.Builder(this).build()
        binding.playerControlView.player = player
        binding.playerControlView2.player = player

        player?.addListener(object : Player.Listener {
            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                super.onMediaItemTransition(mediaItem, reason)
                mediaItem?.apply {
                    songViewModel.songData.postValue(
                        SongItem(
                            mediaId,
                            mediaMetadata.title.toString(),
                            mediaMetadata.composer.toString(),
                            mediaMetadata.albumTitle.toString(),
                            "",
                            null,
                            mediaMetadata.artist.toString(),
                            mediaMetadata.subtitle.toString() == "true",
                            null,
                            "",
                            mediaMetadata.description.toString() == "true"
                        )
                    )
                }
            }
        })
    }

    private fun setPlayList() {
        // songViewModel.getPlayList()
        val startPositionMs = 0L
        val endPositionMs = 60_000L
        val dataSourceFactory = DefaultDataSourceFactory(this, "sample")
        val mediaSourceList = mutableListOf<MediaSource>() // 미디어 소스 리스트 초기화

        songList.forEach { musicItem ->
            val mediaItem = MediaItem.Builder()
                .setUri(musicItem.songPath)
                .setMediaId(musicItem.songId)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(musicItem.songTitle)
                        .setArtist(musicItem.nickname)
                        .setAlbumTitle(musicItem.coverPath)
                        .setDescription(musicItem.isSubscribe.toString())
                        .setSubtitle(musicItem.like.toString())
                        .setComposer(musicItem.songPath)
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
        // concatenatingMediaSource.clear() // 미디어 소스 리스트 초기화
        concatenatingMediaSource.addMediaSources(mediaSourceList)

        player?.prepare(concatenatingMediaSource)

        player?.playWhenReady = true

        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

//    private fun setPlayList() {
//        val startPositionMs = 0L
//        val endPositionMs = 60_000L
//        val dataSourceFactory = DefaultDataSourceFactory(this, "sample")
//        songViewModel.playListData.observe(this) {
//            it?.forEach { musicItem ->
//                val mediaItem = MediaItem.Builder()
//                    .setUri(musicItem.songPath)
//                    .setMediaId(musicItem.songId)
//                    .setMediaMetadata(
//                        MediaMetadata.Builder()
//                            .setTitle(musicItem.songTitle)
//                            .setArtist(musicItem.nickname)
//                            .setAlbumTitle(musicItem.coverPath)
//                            .setDescription(musicItem.isSubscribe.toString())
//                            .setSubtitle(musicItem.like.toString())
//                            .build()
//                    )
//                    .build()
//
//                val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
//                    .createMediaSource(mediaItem)
//                if (musicItem.isSubscribe) {
//                    mediaSourceList.add(mediaSource)
//                } else {
//                    mediaSourceList.add(
//                        ClippingMediaSource(
//                            mediaSource,
//                            startPositionMs * 1000L,
//                            endPositionMs * 1000L
//                        )
//                    )
//                }
//            }
//            concatenatingMediaSource.addMediaSources(mediaSourceList)
//            player?.prepare(concatenatingMediaSource)
//            player?.playWhenReady = true
//        }
//        songViewModel.getPlayList()
//        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
//    }

    fun setPlay() {
        initPlayer()
        setPlayList()
    }

    fun hideBottomNavigation(state: Boolean) {
        when (state) {
            true -> {
                binding.bottomNavigation.visibility = View.GONE
                binding.fabHome.visibility = View.GONE
            }
            else -> {
                binding.bottomNavigation.visibility = View.VISIBLE
                binding.fabHome.visibility = View.VISIBLE
            }
        }
    }
}