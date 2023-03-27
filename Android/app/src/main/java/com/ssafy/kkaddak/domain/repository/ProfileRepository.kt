package com.ssafy.kkaddak.domain.repository

import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.profile.ProfileItem
import com.ssafy.kkaddak.domain.entity.song.SongItem

interface ProfileRepository {

    suspend fun getProfileInfo(nickname: String): Resource<ProfileItem>

    suspend fun getProfileSong(nickname: String): Resource<List<SongItem>>

    suspend fun deleteMySong(songId: String)
}