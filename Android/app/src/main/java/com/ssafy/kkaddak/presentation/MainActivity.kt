package com.ssafy.kkaddak.presentation

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
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
import com.ssafy.kkaddak.common.util.showToastMessage
import com.ssafy.kkaddak.databinding.ActivityMainBinding
import com.ssafy.kkaddak.domain.entity.song.SongItem
import com.ssafy.kkaddak.presentation.base.BaseFragment
import com.ssafy.kkaddak.presentation.songlist.SongViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.util.concurrent.Executor


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var behavior: BottomSheetBehavior<View>
    private lateinit var navHostFragment: NavHostFragment

    private val songViewModel by viewModels<SongViewModel>()
    private val concatenatingMediaSource = ConcatenatingMediaSource()
    private val songList = mutableListOf<SongItem>()

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

        biometricPrompt = setBiometricPrompt()
        promptInfo = setPromptInfo()
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
            if (it.isSubscribe) {
                binding.ivTooltip.visibility = View.GONE
            } else {
                binding.ivTooltip.visibility = View.VISIBLE
            }
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
            ivTooltip.setOnClickListener { this@MainActivity.showToastMessage("구독 시 전체듣기가 가능합니다!") }
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
        this@MainActivity.songId = songId
        player?.stop()
        player?.release()
        CoroutineScope(Dispatchers.Main).launch {
            songViewModel.getSong(this@MainActivity.songId)
            delay(100)
            songViewModel.getPlayList()
            delay(100)
            setPlay(1)
        }
    }

    private fun initPlayer() {
        concatenatingMediaSource.clear()
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
        concatenatingMediaSource.addMediaSources(mediaSourceList)
        player?.prepare(concatenatingMediaSource)
        player?.playWhenReady = true
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    fun setPlay(async: Int) {
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


    private var executor: Executor? = null
    private var biometricPrompt: BiometricPrompt? = null
    private var promptInfo: BiometricPrompt.PromptInfo? = null


    private val loginLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d(TAG, "registerForActivityResult - result : $result")

            if (result.resultCode == Activity.RESULT_OK) {
                Log.d(TAG, "registerForActivityResult - RESULT_OK")
                authenticateToEncrypt()  //생체 인증 가능 여부확인 다시 호출
            } else {
                Log.d(TAG, "registerForActivityResult - NOT RESULT_OK")
            }
        }

    private fun setPromptInfo(): BiometricPrompt.PromptInfo {

        val promptBuilder: BiometricPrompt.PromptInfo.Builder = BiometricPrompt.PromptInfo.Builder()

        promptBuilder.setTitle("Biometric login for my app")
        promptBuilder.setSubtitle("Log in using your biometric credential")
        promptBuilder.setNegativeButtonText("Use account password")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            //  안면인식 ap사용 android 11부터 지원
            promptBuilder.setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
        }

        promptInfo = promptBuilder.build()
        return promptInfo as BiometricPrompt.PromptInfo
    }

    private fun yourResultCallback(result: Boolean): Boolean {
        return result
    }

    private fun setBiometricPrompt(): BiometricPrompt {
        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(
            this@MainActivity,
            executor!!,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        this@MainActivity,
                        """"지문 인식 ERROR [ errorCode: $errorCode, errString: $errString ]""".trimIndent(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(this@MainActivity, "지문 인식 성공", Toast.LENGTH_SHORT).show()
                    yourResultCallback(true)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(this@MainActivity, "지문 인식 실패", Toast.LENGTH_SHORT).show()
                    yourResultCallback(false)
                }

            })
        return biometricPrompt as BiometricPrompt
    }


    /*
    * 생체 인식 인증을 사용할 수 있는지 확인
    * */
    fun authenticateToEncrypt(): Boolean {

        var allow = false
        val biometricManager = BiometricManager.from(this@MainActivity)
        // when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)) {

            //생체 인증 가능
            BiometricManager.BIOMETRIC_SUCCESS -> allow = true

            //기기에서 생체 인증을 지원하지 않는 경우
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> allow = false

            //현재 생체 인증을 사용할 수 없는 경우
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> allow = false

            //생체 인식 정보가 등록되어 있지 않은 경우
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                allow = false

                val dialogBuilder = AlertDialog.Builder(this@MainActivity)
                dialogBuilder
                    .setTitle("나의앱")
                    .setMessage("지문 등록이 필요합니다. 지문등록 설정화면으로 이동하시겠습니까?")
                    .setPositiveButton("확인") { dialog, which -> goBiometricSettings() }
                    .setNegativeButton("취소") { dialog, which -> dialog.cancel() }
                dialogBuilder.show()
            }

            //기타 실패
            else -> allow = false

        }

        //인증 실행하기
        goAuthenticate()

        return allow
    }

    /*
    * 생체 인식 인증 실행
    * */
    private fun goAuthenticate() {
        Log.d(TAG, "goAuthenticate - promptInfo : $promptInfo")
        promptInfo?.let {
            biometricPrompt?.authenticate(it);  //인증 실행
        }
    }

    /*
    * 지문 등록 화면으로 이동
    * */
    fun goBiometricSettings() {
        val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
            putExtra(
                Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )
        }
        loginLauncher.launch(enrollIntent)
    }

    companion object {
        const val TAG: String = "BiometricActivity"
    }
}