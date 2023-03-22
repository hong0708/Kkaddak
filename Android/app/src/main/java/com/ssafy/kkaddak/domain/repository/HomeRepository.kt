package com.ssafy.kkaddak.domain.repository

import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.home.HomeProfile
import com.ssafy.kkaddak.domain.entity.song.SongItem

interface HomeRepository {

    suspend fun getLatestSongs(): Resource<List<SongItem>>

    suspend fun getHomeProfile(): Resource<HomeProfile>
}