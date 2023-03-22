package com.ssafy.kkaddak.domain.usecase.profile

import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.profile.ProfileItem
import com.ssafy.kkaddak.domain.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetProfileInfoUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(nickname: String): Resource<ProfileItem> =
        withContext(Dispatchers.IO) {
            profileRepository.getProfileInfo(nickname)
        }
}