package com.ssafy.kkaddak.data.remote.datasource.profile

import com.google.gson.annotations.SerializedName
import com.ssafy.kkaddak.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.kkaddak.domain.entity.profile.FollowerItem

data class FollowerResponse(
    @SerializedName("followerId")
    val followerId: String,
    @SerializedName("followerUuid")
    val followerUuid: String,
    @SerializedName("profilePath")
    val profilePath: String?,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("isFollowing")
    val isFollowing: Boolean
) : DataToDomainMapper<FollowerItem> {
    override fun toDomainModel(): FollowerItem =
        FollowerItem(followerId, followerUuid, profilePath, nickname, isFollowing)
}