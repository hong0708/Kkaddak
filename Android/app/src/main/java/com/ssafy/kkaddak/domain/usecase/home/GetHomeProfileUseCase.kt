package com.ssafy.kkaddak.domain.usecase.home

import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.home.HomeProfile
import com.ssafy.kkaddak.domain.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetHomeProfileUseCase @Inject constructor(
    private val homeRepository: HomeRepository
){
    suspend operator fun invoke(): Resource<HomeProfile> =
        withContext(Dispatchers.IO) {
            homeRepository.getHomeProfile()
        }
}