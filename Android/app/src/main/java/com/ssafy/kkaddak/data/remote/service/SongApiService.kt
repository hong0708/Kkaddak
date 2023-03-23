package com.ssafy.kkaddak.data.remote.service

import com.ssafy.kkaddak.data.remote.datasource.base.BaseResponse
import com.ssafy.kkaddak.data.remote.datasource.song.SongResponse
import retrofit2.http.*

interface SongApiService {

    @GET("/api/v2/song/list/all")
    suspend fun getMusics(): BaseResponse<List<SongResponse>>

    @POST("/api/v2/song/like")
    suspend fun requestBookmark(@Body songId: String): BaseResponse<Boolean>

    @GET("/api/v2/song/{songId}")
    suspend fun getMusic(@Path("songId") songId: String): BaseResponse<SongResponse>

    @GET("/api/v2/song/myPlay/list")
    suspend fun getPlayList(): BaseResponse<List<SongResponse>>

    @GET("/api/v2/song/search")
    suspend fun searchMusic(
        @Query("keyWord") keyWord: String,
        @Query("genre") filter: String
    ): BaseResponse<List<SongResponse>>

    @DELETE("/api/v2/song/myPlay/{songId}/delete")
    suspend fun deletePlayList(@Path("songId") songId: String)
}