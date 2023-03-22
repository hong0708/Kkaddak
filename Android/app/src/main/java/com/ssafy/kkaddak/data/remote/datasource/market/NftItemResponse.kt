package com.ssafy.kkaddak.data.remote.datasource.market

import com.google.gson.annotations.SerializedName
import com.ssafy.kkaddak.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.kkaddak.domain.entity.market.NftItem

data class NftItemResponse(
    @SerializedName("auctionId")
    val actionId: Int,
    @SerializedName("nftId")
    val nftId: String,
    @SerializedName("creatorName")
    val creatorName: String,
    @SerializedName("songTitle")
    val songTitle: String,
    @SerializedName("nftImagePath")
    val nftImagePath: String,
    @SerializedName("expiredDate")
    val expiredDate: String,
    @SerializedName("bidStartPrice")
    val bidStartPrice: Double,
    @SerializedName("isClosed")
    val isClosed: Boolean,
//    @SerializedName("likeCount")
//    val likeCount: Int
) : DataToDomainMapper<NftItem> {
    override fun toDomainModel(): NftItem =
        NftItem(actionId, nftId, creatorName, songTitle, nftImagePath, expiredDate,  bidStartPrice, isClosed, 0)
}
