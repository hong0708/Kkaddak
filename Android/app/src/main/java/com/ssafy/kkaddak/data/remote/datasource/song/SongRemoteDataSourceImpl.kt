package com.ssafy.kkaddak.data.remote.datasource.song

import com.ssafy.kkaddak.data.remote.service.SongApiService
import javax.inject.Inject

class SongRemoteDataSourceImpl @Inject constructor(
    private val songApiService: SongApiService
) : SongRemoteDataSource {

    override suspend fun getMusics(): List<SongResponse> =
        songApiService.getMusics().data!!

    override suspend fun requestBookmark(songId: String) =
        songApiService.requestBookmark(songId)

    override suspend fun cancelBookmark(songId: String) =
        songApiService.cancelBookmark(songId)

    override suspend fun getMusic(songId: Int): SongResponse =
        songApiService.getMusic(songId).data!!

    override suspend fun getPlayList(): List<SongResponse> =
        songApiService.getPlayList().data!!
}