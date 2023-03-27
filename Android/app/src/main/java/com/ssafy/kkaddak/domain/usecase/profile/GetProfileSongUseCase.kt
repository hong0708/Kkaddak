package com.ssafy.kkaddak.domain.usecase.profile

import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.song.SongItem
import com.ssafy.kkaddak.domain.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetProfileSongUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(nickname: String): Resource<List<SongItem>> =
        withContext(Dispatchers.IO) {
            profileRepository.getProfileSong(nickname)
        }
}