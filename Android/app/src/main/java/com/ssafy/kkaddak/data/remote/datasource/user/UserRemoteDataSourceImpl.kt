package com.ssafy.kkaddak.data.remote.datasource.user

import com.ssafy.kkaddak.data.remote.service.UserApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val userApiService: UserApiService
) : UserRemoteDataSource {

    override suspend fun createUserInfo(
        nickName: String,
        profileImg: MultipartBody.Part?
    ): UserResponse {
        val map = mutableMapOf<String, @JvmSuppressWildcards RequestBody>()
        map["nickname"] = nickName.toRequestBody("text/plain".toMediaTypeOrNull())
        return userApiService.createUserInfo(map, profileImg).data!!
    }

    override suspend fun createUserInfoWithoutImg(
        nickName: String
    ): UserResponse {
        val map = mutableMapOf<String, @JvmSuppressWildcards RequestBody>()
        map["nickname"] = nickName.toRequestBody("text/plain".toMediaTypeOrNull())
        return userApiService.createUserInfo(map, null).data!!
    }

    override suspend fun checkDuplication(nickName: String): Boolean =
        userApiService.checkDuplication(nickName).data!!

    override suspend fun cancelSignUp(): Boolean =
        userApiService.cancelSignUp().data!!

    override suspend fun getUserProfileImg(): UserProfileResponse =
        userApiService.getUserProfile()
}