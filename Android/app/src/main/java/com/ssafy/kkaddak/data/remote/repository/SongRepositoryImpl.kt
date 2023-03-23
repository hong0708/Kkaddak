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

    override suspend fun requestBookmark(songId: String): Resource<Boolean> =
        wrapToResource {
            songRemoteDataSource.requestBookmark(songId)
        }

    override suspend fun getMusic(songId: String): Resource<SongItem> =
        wrapToResource {
            songRemoteDataSource.getMusic(songId).toDomainModel()
        }

    override suspend fun getPlayList(): Resource<List<SongItem>> =
        wrapToResource {
            songRemoteDataSource.getPlayList().map { it.toDomainModel() }
        }

    override suspend fun searchMusic(keyWord: String, filter: String): Resource<List<SongItem>> =
        wrapToResource {
            songRemoteDataSource.searchMusic(keyWord, filter).map { it.toDomainModel() }
        }

    override suspend fun deletePlayList(songId: String) {
        songRemoteDataSource.deletePlayList(songId)
    }
}