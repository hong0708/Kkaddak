package com.ssafy.kkaddak.data.remote.datasource.song

interface SongRemoteDataSource {

    suspend fun getMusics(): List<SongResponse>

    suspend fun requestBookmark(songId: String): Boolean

    suspend fun getMusic(songId: String): SongResponse

    suspend fun getPlayList(): List<SongResponse>

    suspend fun searchMusic(keyWord: String, filter: String): List<SongResponse>
}