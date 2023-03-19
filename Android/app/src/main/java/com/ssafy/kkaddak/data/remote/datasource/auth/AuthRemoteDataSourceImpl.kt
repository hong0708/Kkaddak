package com.ssafy.kkaddak.data.remote.datasource.auth

import com.ssafy.kkaddak.data.remote.service.AuthApiService
import com.ssafy.kkaddak.data.remote.service.RefreshApiService
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authApiService: AuthApiService,
    private val refreshApiService: RefreshApiService
) : AuthRemoteDataSource {

    override suspend fun loginRequest(code: String): AuthResponse =
        authApiService.loginRequest(code).data!!

    override suspend fun getNewToken(): AuthResponse =
        refreshApiService.getNewToken().data!!
}