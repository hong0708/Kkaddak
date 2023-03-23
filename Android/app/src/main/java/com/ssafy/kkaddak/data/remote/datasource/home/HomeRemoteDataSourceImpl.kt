package com.ssafy.kkaddak.data.remote.datasource.home

import com.ssafy.kkaddak.data.remote.datasource.song.SongResponse
import com.ssafy.kkaddak.data.remote.service.HomeApiService
import javax.inject.Inject

class HomeRemoteDataSourceImpl @Inject constructor(
    private val homeApiService: HomeApiService
) : HomeRemoteDataSource {

    override suspend fun getLatestSongs(): List<SongResponse> =
        homeApiService.getLatestSongs().data!!

    override suspend fun getPopularSongs(): List<SongResponse> =
        homeApiService.getPopularSongs().data!!

    override suspend fun getHomeProfile(): HomeProfileResponse =
        homeApiService.getHomeProfile().data!!
}