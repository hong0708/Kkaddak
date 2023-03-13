package com.ssafy.kkaddak.data.remote.service

import com.ssafy.kkaddak.data.remote.datasource.user.UserProfileResponse
import com.ssafy.kkaddak.data.remote.datasource.user.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface UserApiService {
    @Multipart
    @POST("/api/v4/members/sign-up")
    suspend fun createUserInfo(
        @PartMap map: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part image: MultipartBody.Part?
    ): UserResponse

    @GET("/api/v4/members/nicknames/{nickname}/exists")
    suspend fun checkDuplication(@Path("nickname") nickname: String): Boolean

    @POST("/api/v4/members/cancel-sign-up")
    suspend fun cancelSignUp(): Boolean

    @GET("/api/v4/members/member-image")
    suspend fun getUserProfile(): UserProfileResponse
}