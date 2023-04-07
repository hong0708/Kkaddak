package com.ssafy.kkaddak.data.remote.service

import com.ssafy.kkaddak.data.remote.datasource.base.BaseResponse
import com.ssafy.kkaddak.data.remote.datasource.song.SongResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @GET("/api/v2/song/like/list")
    suspend fun getLikeList(): BaseResponse<List<SongResponse>>

    @GET("/api/v2/song/search")
    suspend fun searchMusic(
        @Query("keyWord") keyWord: String,
        @Query("genre") filter: String
    ): BaseResponse<List<SongResponse>>

    @DELETE("/api/v2/song/myPlay/{songId}/delete")
    suspend fun deletePlayList(@Path("songId") songId: String)

    @Multipart
    @POST("/api/v2/song/upload")
    suspend fun uploadMusic(
        @PartMap map: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part coverFile: MultipartBody.Part?,
        @Part songFile: MultipartBody.Part?
    ): BaseResponse<SongResponse>
}