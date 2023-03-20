package com.ssafy.kkaddak.domain.repository

import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.song.SongItem

interface SongRepository {

    suspend fun getMusics(): Resource<List<SongItem>>

    suspend fun getMusic(songId: Int): Resource<SongItem>
}