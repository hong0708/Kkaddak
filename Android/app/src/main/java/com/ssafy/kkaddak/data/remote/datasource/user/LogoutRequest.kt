package com.ssafy.kkaddak.data.remote.datasource.user

import com.google.gson.annotations.SerializedName

data class LogoutRequest(
    @SerializedName("atk")
    val atk: String,
    @SerializedName("rtk")
    val rtk: String
)