package com.ssafy.kkaddak.data.remote.repository

import com.ssafy.kkaddak.common.util.wrapToResource
import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.data.remote.datasource.profile.ProfileRemoteDataSource
import com.ssafy.kkaddak.domain.entity.profile.ProfileItem
import com.ssafy.kkaddak.domain.entity.song.SongItem
import com.ssafy.kkaddak.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileRemoteDataSource: ProfileRemoteDataSource
) : ProfileRepository {

    override suspend fun getProfileInfo(nickname: String): Resource<ProfileItem> =
        wrapToResource {
            profileRemoteDataSource.getProfileInfo(nickname).toDomainModel()
        }

    override suspend fun getProfileSong(nickname: String): Resource<List<SongItem>> =
        wrapToResource {
            profileRemoteDataSource.getProfileSong(nickname).map { it.toDomainModel() }
        }
}