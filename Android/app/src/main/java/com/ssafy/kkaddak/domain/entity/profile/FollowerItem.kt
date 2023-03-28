package com.ssafy.kkaddak.domain.entity.profile

data class FollowerItem(
    val followerId: String,
    val followerUuid: String,
    val profilePath: String?,
    val nickname: String,
    val isFollowing: Boolean
)
