package com.ssafy.kkaddak.data.remote.datasource.user

import com.google.gson.annotations.SerializedName
import com.ssafy.kkaddak.data.remote.datasource.base.DataToDomainMapper

data class UserProfileResponse(
    @SerializedName("profile")
    val profileImg: String?
) : DataToDomainMapper<UserProfile> {
    override fun toDomainModel(): UserProfile = UserProfile(profileImg)
}

