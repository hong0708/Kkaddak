package com.ssafy.kkaddak.data.remote.datasource.market

import com.google.gson.annotations.SerializedName

data class UploadNftItemRequest(
    @SerializedName("creatorName")
    val creatorName: String,
    @SerializedName("nftId")
    val nftId: String,
    @SerializedName("nftImagePath")
    val nftImagePath: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("songTitle")
    val songTitle: String
)