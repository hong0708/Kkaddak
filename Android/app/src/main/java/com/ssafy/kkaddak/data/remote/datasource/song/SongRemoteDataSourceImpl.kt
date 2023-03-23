package com.ssafy.kkaddak.data.remote.datasource.song

import com.ssafy.kkaddak.data.remote.service.SongApiService
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

    override suspend fun searchMusic(keyWord: String, filter: String): List<SongResponse> =
        songApiService.searchMusic(keyWord, filter).data!!

    override suspend fun deletePlayList(songId: String) {
        songApiService.deletePlayList(songId)
    }
}