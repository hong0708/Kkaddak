package com.ssafy.kkaddak.domain.usecase.user

import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.user.User
import com.ssafy.kkaddak.domain.repository.UserRepository
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
        profileImg: MultipartBody.Part?
    ): Resource<User> = withContext(Dispatchers.IO) {
        userRepository.createUserInfo(nickname, profileImg)
    }

    suspend fun createUserInfoWithoutImg(nickname: String): Resource<User> =
        withContext(Dispatchers.IO) {
            userRepository.createUserInfoWithoutImg(nickname)
        }
}