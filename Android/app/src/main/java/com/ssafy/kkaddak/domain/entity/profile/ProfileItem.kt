package com.ssafy.kkaddak.domain.entity.profile

data class ProfileItem(
    val memberId: String,
    val nickname: String,
    val profilepath: String,
    val account: String?,
    val myFollowers: Int,
    val myFollowings: Int,
    val mySongs: Int,
    val mine: Boolean,
)