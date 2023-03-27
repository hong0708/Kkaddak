package com.ssafy.kkaddak.data.remote.datasource.market

import com.google.gson.annotations.SerializedName
import com.ssafy.kkaddak.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.kkaddak.domain.entity.market.NftItem

data class NftItemResponse(
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
    val isClose: Boolean,
    @SerializedName("cntLikeMarket")
    val cntLikeMarket: Int,
    @SerializedName("isLike")
    val isLike: Boolean
) : DataToDomainMapper<NftItem> {
    override fun toDomainModel(): NftItem =
        NftItem(marketId, nftId, creatorName, songTitle, nftImagePath, createdAt,  price, isClose, cntLikeMarket, isLike)
}
