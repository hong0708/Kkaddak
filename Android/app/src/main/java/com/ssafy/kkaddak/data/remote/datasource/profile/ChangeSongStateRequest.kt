package com.ssafy.kkaddak.data.remote.datasource.profile

import com.google.gson.annotations.SerializedName

data class ChangeSongStateRequest(
    @SerializedName("songStatus")
    val songStatus: String,
    @SerializedName("songUUID")
    val songUUID: String
)
