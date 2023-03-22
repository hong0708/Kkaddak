package com.ssafy.kkaddak.data.remote.datasource.song

interface SongRemoteDataSource {

    suspend fun getMusics(): List<SongResponse>

    suspend fun requestBookmark(songId: String)

    suspend fun cancelBookmark(songId: String)

    suspend fun getMusic(songId: Int): SongResponse

    suspend fun getPlayList(): List<SongResponse>
}