package com.ssafy.kkaddak.domain.repository

import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.data.remote.datasource.auth.AuthRequest
import com.ssafy.kkaddak.domain.entity.auth.Token

interface AuthRepository {

    suspend fun loginRequest(body: AuthRequest): Resource<Token>

    suspend fun getNewToken(): Resource<Token>
}