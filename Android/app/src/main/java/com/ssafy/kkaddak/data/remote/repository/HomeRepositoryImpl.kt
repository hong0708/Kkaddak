package com.ssafy.kkaddak.data.remote.repository

import com.ssafy.kkaddak.common.util.wrapToResource
import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.data.remote.datasource.home.HomeRemoteDataSource
import com.ssafy.kkaddak.domain.entity.home.HomeProfile
import com.ssafy.kkaddak.domain.entity.song.SongItem
import com.ssafy.kkaddak.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeRemoteDataSource: HomeRemoteDataSource
) : HomeRepository {

    override suspend fun getLatestSongs(): Resource<List<SongItem>> =
        wrapToResource {
            homeRemoteDataSource.getLatestSongs().map { it.toDomainModel() }
        }

    override suspend fun getPopularSongs(): Resource<List<SongItem>> =
        wrapToResource {
            homeRemoteDataSource.getPopularSongs().map { it.toDomainModel() }
        }

    override suspend fun getHomeProfile(): Resource<HomeProfile> =
        wrapToResource {
            homeRemoteDataSource.getHomeProfile().toDomainModel()
        }
}