package com.ssafy.kkaddak.data.remote.datasource.song

interface SongRemoteDataSource {

    suspend fun getMusics(): List<SongResponse>
}