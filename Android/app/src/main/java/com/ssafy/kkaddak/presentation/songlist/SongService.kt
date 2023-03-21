package com.ssafy.kkaddak.presentation.songlist

import android.app.*
import android.content.Intent
import android.os.IBinder
import com.google.android.exoplayer2.*

class SongService : Service() {
    private lateinit var player: ExoPlayer

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        player.playWhenReady = true
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        player = ExoPlayer.Builder(this).build()

        player.addListener(object : Player.Listener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                when (playbackState) {
                    Player.STATE_READY -> {}
                    Player.STATE_ENDED -> {}
                    Player.STATE_BUFFERING ->{}
                    Player.STATE_IDLE -> {}
                    else -> {}
                }
            }
        })
    }

    override fun onDestroy() {
        player.release()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}