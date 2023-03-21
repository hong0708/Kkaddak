package com.ssafy.kkaddak.data.remote.service

import com.ssafy.kkaddak.data.remote.datasource.base.BaseResponse
import com.ssafy.kkaddak.data.remote.datasource.profile.ProfileResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileApiService {

    @GET("/api/v1/members/profile/{nickname}")
    suspend fun getProfileInfo(@Path("nickname") nickname: String): BaseResponse<ProfileResponse>
}