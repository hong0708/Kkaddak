package com.ssafy.kkaddak.data.remote.datasource.song

import com.ssafy.kkaddak.data.remote.service.SongApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class SongRemoteDataSourceImpl @Inject constructor(
    private val songApiService: SongApiService
) : SongRemoteDataSource {

    override suspend fun getMusics(): List<SongResponse> =
        songApiService.getMusics().data!!

    override suspend fun requestBookmark(songId: String): Boolean =
        songApiService.requestBookmark(songId).data!!

    override suspend fun getMusic(songId: String): SongResponse =
        songApiService.getMusic(songId).data!!

    override suspend fun getPlayList(): List<SongResponse> =
        songApiService.getPlayList().data!!

    override suspend fun getLikeList(): List<SongResponse> =
        songApiService.getLikeList().data!!

    override suspend fun searchMusic(keyWord: String, filter: String): List<SongResponse> =
        songApiService.searchMusic(keyWord, filter).data!!

    override suspend fun deletePlayList(songId: String) {
        songApiService.deletePlayList(songId)
    }

    override suspend fun uploadMusic(
        coverFile: MultipartBody.Part?,
        songFile: MultipartBody.Part?,
        moods: List<String>,
        genre: String,
        songTitle: String
    ): SongResponse {
        val map = mutableMapOf<String, @JvmSuppressWildcards RequestBody>()
        moods.forEachIndexed { index, value ->
            val listBody = value.toRequestBody("text/plain".toMediaTypeOrNull())
            map["moods[$index]"] = listBody
        }
        map["genre"] = genre.toRequestBody("text/plain".toMediaTypeOrNull())
        map["songTitle"] = songTitle.toRequestBody("text/plain".toMediaTypeOrNull())
        return songApiService.uploadMusic(map, coverFile, songFile).data!!
    }
}