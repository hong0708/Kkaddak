package com.ssafy.kkaddak.data.remote.service

import com.ssafy.kkaddak.data.remote.datasource.auth.AuthResponse
import com.ssafy.kkaddak.data.remote.datasource.base.BaseResponse
import retrofit2.http.GET

interface RefreshApiService {
    @GET("/api/v1/members/reissue")
    suspend fun getNewToken(): BaseResponse<AuthResponse>
}