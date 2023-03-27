package com.ssafy.kkaddak.domain.usecase.song

import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.song.SongItem
import com.ssafy.kkaddak.domain.repository.SongRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UploadSongUseCase @Inject constructor(
    private val songRepository: SongRepository
) {
    suspend operator fun invoke(
        coverFile: MultipartBody.Part?,
        songFile: MultipartBody.Part?,
        moods: List<String>,
        genre: String,
        songTitle: String
    ): Resource<SongItem> = withContext(Dispatchers.IO) {
        songRepository.uploadMusic(coverFile, songFile, moods, genre, songTitle)
    }
}