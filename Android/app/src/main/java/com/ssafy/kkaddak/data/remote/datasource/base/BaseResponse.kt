package com.ssafy.kkaddak.data.remote.datasource.base

import com.google.gson.annotations.SerializedName

open class BaseResponse<T>(
    @SerializedName("statusCode")
    var statusCode: Int? = null,
    @SerializedName("statusMessage")
    var statusMessage: String? = null,
    @SerializedName("data")
    var data: T? = null,
)