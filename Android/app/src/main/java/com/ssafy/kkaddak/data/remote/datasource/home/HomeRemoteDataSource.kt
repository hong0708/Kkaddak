package com.ssafy.kkaddak.data.remote.datasource.home

import com.ssafy.kkaddak.data.remote.datasource.song.SongResponse

interface HomeRemoteDataSource {

    suspend fun getLatestSongs(): List<SongResponse>

    suspend fun getPopularSongs(): List<SongResponse>

    suspend fun getHomeProfile(): HomeProfileResponse
}