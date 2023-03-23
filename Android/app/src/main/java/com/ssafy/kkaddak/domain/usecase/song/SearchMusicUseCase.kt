package com.ssafy.kkaddak.domain.usecase.song

import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.song.SongItem
import com.ssafy.kkaddak.domain.repository.SongRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchMusicUseCase @Inject constructor(
    private val songRepository: SongRepository
) {
    suspend operator fun invoke(keyWord: String, filter: String): Resource<List<SongItem>> =
        withContext(Dispatchers.IO) {
            songRepository.searchMusic(keyWord, filter)
        }
}