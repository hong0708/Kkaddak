package com.ssafy.kkaddak.domain.usecase.profile

import com.ssafy.kkaddak.domain.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UploadThumbnailUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(nftImageUrl: String) {
        withContext(Dispatchers.IO) {
            profileRepository.uploadThumbnail(nftImageUrl)
        }
    }
}