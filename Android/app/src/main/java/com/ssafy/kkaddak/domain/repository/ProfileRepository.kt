package com.ssafy.kkaddak.domain.repository

import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.data.remote.datasource.profile.NFTImageResponse
import com.ssafy.kkaddak.domain.entity.profile.FollowerItem
import com.ssafy.kkaddak.domain.entity.profile.ProfileItem
import com.ssafy.kkaddak.domain.entity.song.SongItem
import okhttp3.MultipartBody

interface ProfileRepository {

    suspend fun getProfileInfo(nickname: String): Resource<ProfileItem>

    suspend fun getProfileSong(nickname: String): Resource<List<SongItem>>

    suspend fun deleteMySong(songId: String)

    suspend fun followArtist(artistId: String)

    suspend fun unfollowArtist(artistId: String)

    suspend fun getFollowers(lastId: Int, limit: Int): Resource<List<FollowerItem>>

    suspend fun getFollowings(lastId: Int, limit: Int): Resource<List<FollowerItem>>

    suspend fun uploadNFTImage(
        songId: String,
        nftImg: MultipartBody.Part?
    ): Resource<NFTImageResponse>
}