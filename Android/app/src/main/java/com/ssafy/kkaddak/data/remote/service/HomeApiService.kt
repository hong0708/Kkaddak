package com.ssafy.kkaddak.data.remote.service

import com.ssafy.kkaddak.data.remote.datasource.base.BaseResponse
import com.ssafy.kkaddak.data.remote.datasource.song.SongResponse
import retrofit2.http.GET

interface HomeApiService {

    @GET("/api/v2/song/list/latest")
    suspend fun getLatestSongs(): BaseResponse<List<SongResponse>>
}