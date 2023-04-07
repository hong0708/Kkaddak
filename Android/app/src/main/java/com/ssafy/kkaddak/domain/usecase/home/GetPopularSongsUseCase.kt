package com.ssafy.kkaddak.domain.usecase.home

import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.song.SongItem
import com.ssafy.kkaddak.domain.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPopularSongsUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(): Resource<List<SongItem>> =
        withContext(Dispatchers.IO) {
            homeRepository.getPopularSongs()
        }
}