package com.ssafy.kkaddak.data.remote.repository

import com.ssafy.kkaddak.common.util.wrapToResource
import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.data.remote.datasource.song.SongRemoteDataSource
import com.ssafy.kkaddak.domain.entity.song.SongItem
import com.ssafy.kkaddak.domain.repository.SongRepository
import javax.inject.Inject

class SongRepositoryImpl @Inject constructor(
    private val songRemoteDataSource: SongRemoteDataSource
) : SongRepository {

    override suspend fun getMusics(): Resource<List<SongItem>> =
        wrapToResource {
            songRemoteDataSource.getMusics().map { it.toDomainModel() }
        }

    override suspend fun getMusic(songId: Int): Resource<SongItem> =
        wrapToResource {
            songRemoteDataSource.getMusic(songId).toDomainModel()
        }
}