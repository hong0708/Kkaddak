package com.ssafy.kkaddak.domain.usecase.user

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun createUserInfo(
        nickname: String,
        profileImg: MultipartBody.Part?,
        fcmToken: String
    ): Resource<User> = withContext(Dispatchers.IO) {
        userRepository.createUserInfo(nickname, profileImg, fcmToken)
    }

    suspend fun createUserInfoWithoutImg(
        nickname: String,
        fcmToken: String
    ): Resource<User> = withContext(Dispatchers.IO) {
        userRepository.createUserInfoWithoutImg(nickname, fcmToken)
    }
}