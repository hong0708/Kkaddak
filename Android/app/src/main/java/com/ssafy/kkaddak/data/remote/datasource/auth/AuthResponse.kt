package com.ssafy.kkaddak.data.remote.datasource.auth

import com.google.gson.annotations.SerializedName
import com.ssafy.kkaddak.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.kkaddak.domain.entity.auth.Token

data class AuthResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("isExist")
    val isExist: Boolean,
    @SerializedName("refreshToken")
    val refreshToken: String
) : DataToDomainMapper<Token> {
    override fun toDomainModel(): Token =
        Token(
            accessToken = accessToken,
            refreshToken = refreshToken,
            isExist = isExist
        )
}