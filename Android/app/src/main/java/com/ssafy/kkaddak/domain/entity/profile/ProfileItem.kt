package com.ssafy.kkaddak.domain.entity.profile

data class ProfileItem(
    val memberId: String,
    val nickname: String,
    val profilepath: String,
    val account: String?,
    val mine: Boolean,
)