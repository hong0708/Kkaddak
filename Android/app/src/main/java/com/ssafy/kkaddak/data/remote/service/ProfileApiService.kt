package com.ssafy.kkaddak.data.remote.service

import com.ssafy.kkaddak.data.remote.datasource.base.BaseResponse
import com.ssafy.kkaddak.data.remote.datasource.profile.ProfileResponse
import com.ssafy.kkaddak.data.remote.datasource.song.SongResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileApiService {

    @GET("/api/v1/members/profile/{nickname}")
    suspend fun getProfileInfo(@Path("nickname") nickname: String): BaseResponse<ProfileResponse>

    @GET("/api/v2/song/profile/{nickname}")
    suspend fun getProfileSong(@Path("nickname") nickname: String): BaseResponse<List<SongResponse>>

    @DELETE("/api/v2/song/delete/{songId}")
    suspend fun deleteMySong(@Path("songId") songId: String)
}