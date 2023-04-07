package com.ssafy.kkaddak.domain.usecase.profile

import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChangeSongStateUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(songStatus: String, songUUID: String): Resource<Boolean> =
        withContext(Dispatchers.IO) {
            profileRepository.changeSongState(songStatus, songUUID)
        }
}