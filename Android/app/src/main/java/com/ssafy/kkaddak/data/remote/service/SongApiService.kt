package com.ssafy.kkaddak.data.remote.service

import com.ssafy.kkaddak.data.remote.datasource.song.SongResponse
import retrofit2.http.GET

interface SongApiService {
    @GET("/v3/e4db045a-23a9-4b49-a3fc-78cf51f3f964")
    suspend fun getMusics(): List<SongResponse>
}