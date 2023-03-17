package com.ssafy.kkaddak.domain.usecase

import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.SongItem
import com.ssafy.kkaddak.domain.repository.SongRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class getSongsUseCase @Inject constructor(
    private val songRepository: SongRepository
) {
    suspend operator fun invoke(): Resource<List<SongItem>> =
        withContext(Dispatchers.IO) {
            songRepository.getMusics()
        }
}