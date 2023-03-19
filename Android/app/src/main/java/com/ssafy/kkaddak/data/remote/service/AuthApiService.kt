package com.ssafy.kkaddak.data.remote.service

import com.ssafy.kkaddak.data.remote.datasource.auth.AuthResponse
import com.ssafy.kkaddak.data.remote.datasource.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApiService {
    @GET("/api/v1/members/kakao-login")
    suspend fun loginRequest(@Query("accessToken") code: String): BaseResponse<AuthResponse>
}