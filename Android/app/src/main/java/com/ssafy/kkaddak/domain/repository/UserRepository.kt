package com.ssafy.kkaddak.domain.repository

import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.user.User
import okhttp3.MultipartBody

interface UserRepository {
    suspend fun createUserInfo(
        nickName: String,
        profileImg: MultipartBody.Part?
    ): Resource<User>

    suspend fun createUserInfoWithoutImg(nickName: String): Resource<User>

    suspend fun checkDuplication(nickName: String): Resource<Boolean>

    suspend fun cancelSignUp(): Resource<Boolean>

    suspend fun getUserProfile(): Resource<String?>

    suspend fun logout(atk: String, rtk: String): Resource<Boolean>
}