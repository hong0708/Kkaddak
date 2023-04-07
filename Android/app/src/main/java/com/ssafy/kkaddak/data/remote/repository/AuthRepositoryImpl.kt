package com.ssafy.kkaddak.data.remote.repository

import com.ssafy.kkaddak.common.util.wrapToResource
import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.data.remote.datasource.auth.AuthRemoteDataSource
import com.ssafy.kkaddak.data.remote.datasource.auth.AuthRequest
import com.ssafy.kkaddak.domain.entity.auth.Token
import com.ssafy.kkaddak.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun loginRequest(body: AuthRequest): Resource<Token> =
        wrapToResource {
            authRemoteDataSource.loginRequest(body.accessToken).toDomainModel()
        }

    override suspend fun getNewToken(): Resource<Token> =
        wrapToResource {
            authRemoteDataSource.getNewToken().toDomainModel()
        }
}