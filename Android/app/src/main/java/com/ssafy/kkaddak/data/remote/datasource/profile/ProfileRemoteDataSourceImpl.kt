package com.ssafy.kkaddak.data.remote.datasource.profile

import com.ssafy.kkaddak.data.remote.datasource.song.SongResponse
import com.ssafy.kkaddak.data.remote.service.ProfileApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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

    override suspend fun uploadThumbnail(nftImageUrl: String) {
        profileApiService.uploadThumbnail(nftImageUrl)
    }

    override suspend fun editUserInfo(
        isUpdating: Boolean,
        nickname: String,
        profileImg: MultipartBody.Part?
    ) {
        val map = mutableMapOf<String, @JvmSuppressWildcards RequestBody>()
        map["nickname"] = nickname.toRequestBody("text/plain".toMediaTypeOrNull())
        map["isUpdating"] = isUpdating.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        profileApiService.editUserInfo(map, profileImg)
    }
}