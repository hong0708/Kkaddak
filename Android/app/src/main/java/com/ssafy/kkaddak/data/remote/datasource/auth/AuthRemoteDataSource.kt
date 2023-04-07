package com.ssafy.kkaddak.data.remote.datasource.auth

interface AuthRemoteDataSource {

    suspend fun loginRequest(code: String): AuthResponse

    suspend fun getNewToken(): AuthResponse
}