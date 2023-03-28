package com.ssafy.kkaddak.domain.usecase.profile

import com.ssafy.kkaddak.domain.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestFollowUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend fun followArtist(artistId: String) {
        withContext(Dispatchers.IO) {
            profileRepository.followArtist(artistId)
        }
    }

    suspend fun unfollowArtist(artistId: String) {
        withContext(Dispatchers.IO) {
            profileRepository.unfollowArtist(artistId)
        }
    }
}