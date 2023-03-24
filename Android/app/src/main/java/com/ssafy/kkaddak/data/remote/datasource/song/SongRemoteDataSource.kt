package com.ssafy.kkaddak.data.remote.datasource.song

import okhttp3.MultipartBody

interface SongRemoteDataSource {

    suspend fun getMusics(): List<SongResponse>

    suspend fun requestBookmark(songId: String): Boolean

    suspend fun getMusic(songId: String): SongResponse

    suspend fun getPlayList(): List<SongResponse>

    suspend fun searchMusic(keyWord: String, filter: String): List<SongResponse>

    suspend fun deletePlayList(songId: String)

    suspend fun uploadMusic(
        coverFile: MultipartBody.Part?,
        songFile: MultipartBody.Part?,
        moods: List<String>,
        genre: String,
        songTitle: String
    ): SongResponse
}