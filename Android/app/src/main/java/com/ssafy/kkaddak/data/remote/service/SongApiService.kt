package com.ssafy.kkaddak.data.remote.service

import com.ssafy.kkaddak.data.remote.datasource.base.BaseResponse
import com.ssafy.kkaddak.data.remote.datasource.song.SongResponse
import retrofit2.http.*

interface SongApiService {

    @GET("/api/v2/song/list/all")
    suspend fun getMusics(): BaseResponse<List<SongResponse>>

    @POST("/api/v2/song/like/{songId}")
    suspend fun requestBookmark(@Body songId: String)

    @DELETE("/api/v2/song/like/{songId}")
    suspend fun cancelBookmark(@Path("songId") songId: String)
    
    @GET("/api/v2/song/{id}")
    suspend fun getMusic(@Path("id") songId: String): BaseResponse<SongResponse>

    @GET("/api/v2/song/song/myPlay/list")
    suspend fun getPlayList(): BaseResponse<List<SongResponse>>
}