package com.ssafy.kkaddak.domain.entity.song

data class SongItem(
    val songId: Int,
    val songTitle: String,
    val songPath: String,
    val coverPath: String,
    val genre: String,
    val mood: String,
    val nickname: String?
)
