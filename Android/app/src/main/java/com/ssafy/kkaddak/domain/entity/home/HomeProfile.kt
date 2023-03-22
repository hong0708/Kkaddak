package com.ssafy.kkaddak.domain.entity.home

data class HomeProfile(
    val memberId: String,
    val nickname: String,
    val profilepath: String?,
    val mySongs: Int,
)