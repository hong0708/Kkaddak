package com.ssafy.kkaddak.data.remote.service

import com.ssafy.kkaddak.data.remote.datasource.auth.AuthResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApiService {
    @GET("/api/v4/members/login-kakao")
    suspend fun loginRequest(@Query("accessToken") code: String): AuthResponse
}