package com.ssafy.kkaddak.data.remote.datasource.user

import com.google.gson.annotations.SerializedName

data class WalletAccountRequest(
    @SerializedName("account")
    val account: String
)