package com.ssafy.kkaddak.data.remote.datasource.profile

import com.ssafy.kkaddak.data.remote.datasource.song.SongResponse
import com.ssafy.kkaddak.data.remote.service.ProfileApiService
import javax.inject.Inject

class ProfileRemoteDataSourceImpl @Inject constructor(
    private val profileApiService: ProfileApiService
) : ProfileRemoteDataSource {

    override suspend fun getProfileInfo(nickname: String): ProfileResponse =
        profileApiService.getProfileInfo(nickname).data!!

    override suspend fun getProfileSong(nickname: String): List<SongResponse> =
        profileApiService.getProfileSong(nickname).data!!

    override suspend fun deleteMySong(songId: String) {
        profileApiService.deleteMySong(songId)
    }

    override suspend fun followArtist(artistId: String) {
        profileApiService.followArtist(artistId)
    }

    override suspend fun unfollowArtist(artistId: String) {
        profileApiService.unfollowArtist(artistId)
    }

    override suspend fun getFollowers(lastId: Int, limit: Int): List<FollowerResponse> =
        profileApiService.getFollowers(lastId, limit).data!!

    override suspend fun getFollowings(lastId: Int, limit: Int): List<FollowerResponse> =
        profileApiService.getFollowings(lastId, limit).data!!
}