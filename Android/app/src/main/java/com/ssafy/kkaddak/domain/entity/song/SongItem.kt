package com.ssafy.kkaddak.domain.entity.song

data class SongItem(
    val songId: String,
    val songTitle: String,
    val songPath: String,
    val coverPath: String,
    val genre: String,
    val mood: List<String>?,
    val nickname: String?,
    val like: Boolean,
    val combination: List<Int>?,
    val songStatus: String?
)
