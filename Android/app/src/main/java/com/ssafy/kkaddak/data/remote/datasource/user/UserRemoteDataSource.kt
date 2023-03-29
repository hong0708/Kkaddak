package com.ssafy.kkaddak.data.remote.datasource.user

import okhttp3.MultipartBody

interface UserRemoteDataSource {
    suspend fun createUserInfo(
        nickName: String,
        profileImg: MultipartBody.Part?
    ): UserResponse

    suspend fun createUserInfoWithoutImg(nickName: String): UserResponse

    suspend fun checkDuplication(nickName: String): Boolean

    suspend fun cancelSignUp(): Boolean

    suspend fun getUserProfileImg(): String?

    suspend fun logout(atk: String, rtk: String): Boolean

    suspend fun registerWalletAccount(walletAccount:String): Boolean
}
