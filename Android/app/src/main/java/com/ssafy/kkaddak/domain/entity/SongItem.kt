package com.ssafy.kkaddak.domain.entity

data class SongItem(
    val id: Long,
    val track: String,
    val streamUrl: String,
    val artist: String,
    val coverUrl: String,
    val isPlaying: Boolean = false
)
