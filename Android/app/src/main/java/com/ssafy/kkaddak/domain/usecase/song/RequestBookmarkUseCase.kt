package com.ssafy.kkaddak.domain.usecase.song

import com.ssafy.kkaddak.domain.repository.SongRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestBookmarkUseCase @Inject constructor(
    private val songRepository: SongRepository
) {
    suspend operator fun invoke(songId: String) {
        withContext(Dispatchers.IO) {
            songRepository.requestBookmark(songId)
        }
    }
}