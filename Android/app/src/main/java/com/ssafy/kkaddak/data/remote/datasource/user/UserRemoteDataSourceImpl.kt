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
        map["nickName"] = nickName.toRequestBody("text/plain".toMediaTypeOrNull())
        return userApiService.createUserInfo(map, profileImg)
    }

    override suspend fun createUserInfoWithoutImg(
        nickName: String
    ): UserResponse {
        val map = mutableMapOf<String, @JvmSuppressWildcards RequestBody>()
        map["nickName"] = nickName.toRequestBody("text/plain".toMediaTypeOrNull())
        return userApiService.createUserInfo(map, null)
    }

    override suspend fun checkDuplication(nickName: String): Boolean =
        userApiService.checkDuplication(nickName)

    override suspend fun cancelSignUp(): Boolean =
        userApiService.cancelSignUp()

    override suspend fun getUserProfileImg(): UserProfileResponse =
        userApiService.getUserProfile()
}