package com.ssafy.kkaddak.data.remote.datasource.profile

import com.ssafy.kkaddak.data.remote.datasource.song.SongResponse

interface ProfileRemoteDataSource {

    suspend fun getProfileInfo(nickname: String): ProfileResponse

    suspend fun getProfileSong(nickname: String): List<SongResponse>

    suspend fun deleteMySong(songId: String)

    suspend fun followArtist(artistId: String)

    suspend fun unfollowArtist(artistId: String)
}