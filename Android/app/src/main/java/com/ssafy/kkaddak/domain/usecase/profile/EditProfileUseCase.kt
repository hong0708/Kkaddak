package com.ssafy.kkaddak.domain.usecase.profile

import com.ssafy.kkaddak.domain.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EditProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(
        isUpdating: Boolean,
        nickname: String,
        profileImg: MultipartBody.Part?
    ) {
        withContext(Dispatchers.IO) {
            profileRepository.editUserInfo(isUpdating, nickname, profileImg)
        }
    }
}