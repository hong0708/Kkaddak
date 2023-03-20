package com.ssafy.kkaddak.data.remote.datasource.user

import com.google.gson.annotations.SerializedName
import com.ssafy.kkaddak.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.kkaddak.domain.entity.user.User

data class UserResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("profilePath")
    val profilePath: String?
) : DataToDomainMapper<User> {
    override fun toDomainModel(): User =
        User(email, nickname, profilePath)
}