package com.ssafy.kkaddak.data.remote.datasource.song

interface SongRemoteDataSource {

    suspend fun getMusics(): List<SongResponse>

    suspend fun requestBookmark(songId: String)

    suspend fun cancelBookmark(songId: String)
}