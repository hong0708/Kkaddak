package com.ssafy.kkaddak.data.remote.datasource.profile

interface ProfileRemoteDataSource {

    suspend fun getProfileInfo(nickname: String): ProfileResponse
}