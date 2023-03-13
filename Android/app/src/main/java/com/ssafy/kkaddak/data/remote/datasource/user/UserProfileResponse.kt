package com.ssafy.kkaddak.data.remote.datasource.user

import com.google.gson.annotations.SerializedName
import com.ssafy.kkaddak.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.kkaddak.domain.entity.user.UserProfile

data class UserProfileResponse(
    @SerializedName("profile")
    val profileImg: String?
) : DataToDomainMapper<UserProfile> {
    override fun toDomainModel(): UserProfile = UserProfile(profileImg)
}

