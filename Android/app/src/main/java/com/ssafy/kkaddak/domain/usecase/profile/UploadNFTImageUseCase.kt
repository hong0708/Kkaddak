package com.ssafy.kkaddak.domain.usecase.profile

import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.data.remote.datasource.profile.NFTImageResponse
import com.ssafy.kkaddak.domain.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject

class UploadNFTImageUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(
        songId: String,
        nftImg: MultipartBody.Part?
    ): Resource<NFTImageResponse> =
        withContext(Dispatchers.IO) {
            profileRepository.uploadNFTImage(songId, nftImg)
        }
}