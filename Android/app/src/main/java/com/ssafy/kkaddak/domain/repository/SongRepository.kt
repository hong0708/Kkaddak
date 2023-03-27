package com.ssafy.kkaddak.domain.repository

import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.song.SongItem
import okhttp3.MultipartBody

interface SongRepository {

    suspend fun getMusics(): Resource<List<SongItem>>

    suspend fun requestBookmark(songId: String): Resource<Boolean>

    suspend fun getMusic(songId: String): Resource<SongItem>

    suspend fun getPlayList(): Resource<List<SongItem>>

    suspend fun searchMusic(keyWord: String, filter: String): Resource<List<SongItem>>

    suspend fun deletePlayList(songId: String)

    suspend fun uploadMusic(
        coverFile: MultipartBody.Part?,
        songFile: MultipartBody.Part?,
        moods: List<String>,
        genre: String,
        songTitle: String
    ): Resource<SongItem>
}