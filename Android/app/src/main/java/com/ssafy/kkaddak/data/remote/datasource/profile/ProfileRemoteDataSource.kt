package com.ssafy.kkaddak.data.remote.datasource.profile

import com.ssafy.kkaddak.data.remote.datasource.song.SongResponse
import okhttp3.MultipartBody

interface ProfileRemoteDataSource {

    suspend fun getProfileInfo(nickname: String): ProfileResponse

    suspend fun getProfileSong(nickname: String): List<SongResponse>

    suspend fun deleteMySong(songId: String)

    suspend fun followArtist(artistId: String)

    suspend fun unfollowArtist(artistId: String)

    suspend fun getFollowers(lastId: Int, limit: Int): List<FollowerResponse>

    suspend fun getFollowings(lastId: Int, limit: Int): List<FollowerResponse>

    suspend fun uploadNFTImage(
        songId: String,
        nftImg: MultipartBody.Part?
    ): NFTImageResponse
}