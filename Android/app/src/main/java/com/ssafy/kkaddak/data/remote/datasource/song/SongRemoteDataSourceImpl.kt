package com.ssafy.kkaddak.data.remote.datasource.song

import com.ssafy.kkaddak.data.remote.service.SongApiService
import javax.inject.Inject

class SongRemoteDataSourceImpl @Inject constructor(
    private val songApiService: SongApiService
) : SongRemoteDataSource {

    override suspend fun getMusics(): List<SongResponse> =
        songApiService.getMusics().data!!
}