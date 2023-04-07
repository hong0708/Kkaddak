package com.ssafy.kkaddak.data.remote.service

import com.ssafy.kkaddak.data.remote.datasource.base.BaseResponse
import com.ssafy.kkaddak.data.remote.datasource.home.HomeProfileResponse
import com.ssafy.kkaddak.data.remote.datasource.song.SongResponse
import retrofit2.http.GET

interface HomeApiService {

    @GET("/api/v2/song/list/latest")
    suspend fun getLatestSongs(): BaseResponse<List<SongResponse>>

    @GET("/api/v2/song/list/popularity")
    suspend fun getPopularSongs(): BaseResponse<List<SongResponse>>

    @GET("/api/v1/members/my-profile")
    suspend fun getHomeProfile(): BaseResponse<HomeProfileResponse>
}