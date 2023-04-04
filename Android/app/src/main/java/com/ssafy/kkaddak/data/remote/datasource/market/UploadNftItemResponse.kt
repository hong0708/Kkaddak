package com.ssafy.kkaddak.data.remote.datasource.market

import com.google.gson.annotations.SerializedName
import com.ssafy.kkaddak.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.kkaddak.domain.entity.market.UploadNftItem

data class UploadNftItemResponse(
    @SerializedName("marketId")
    val marketId: Int,
    @SerializedName("nftId")
    val nftId: String,
    @SerializedName("creatorName")
    val creatorName: String,
    @SerializedName("songTitle")
    val songTitle: String,
    @SerializedName("nftImagePath")
    val nftImagePath: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("isClose")
    val isClose: Boolean
) : DataToDomainMapper<UploadNftItem> {
    override fun toDomainModel(): UploadNftItem =
        UploadNftItem(marketId, nftId, creatorName, songTitle, nftImagePath, createdAt,  price, isClose)
}
