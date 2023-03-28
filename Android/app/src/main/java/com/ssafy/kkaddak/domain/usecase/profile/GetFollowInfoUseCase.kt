package com.ssafy.kkaddak.domain.usecase.profile

import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.profile.FollowerItem
import com.ssafy.kkaddak.domain.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetFollowInfoUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend fun getFollowers(lastId: Int, limit: Int): Resource<List<FollowerItem>> =
        withContext(Dispatchers.IO) {
            profileRepository.getFollowers(lastId, limit)
        }

    suspend fun getFollowings(lastId: Int, limit: Int): Resource<List<FollowerItem>> =
        withContext(Dispatchers.IO) {
            profileRepository.getFollowings(lastId, limit)
        }
}