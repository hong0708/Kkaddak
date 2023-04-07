package com.ssafy.kkaddak.domain.entity.auth

data class Token(
    val accessToken: String?,
    val refreshToken: String,
    val isExist: Boolean
)