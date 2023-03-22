package com.ssafy.kkaddak.data.remote.service

import com.ssafy.kkaddak.data.remote.datasource.base.BaseResponse
import com.ssafy.kkaddak.data.remote.datasource.song.SongResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface SongApiService {

    @GET("/api/v2/song/list/all")
    suspend fun getMusics(): BaseResponse<List<SongResponse>>

    @GET("/api/v2/song/{id}")
    suspend fun getMusic(@Path("id") songId: String): BaseResponse<SongResponse>

    @GET("/api/v2/song/song/myPlay/list")
    suspend fun getPlayList(): BaseResponse<List<SongResponse>>
}