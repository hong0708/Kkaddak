package com.ssafy.kkaddak.data.remote.datasource.market

import com.google.gson.annotations.SerializedName
import com.ssafy.kkaddak.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.kkaddak.domain.entity.market.HistoryItem
import com.ssafy.kkaddak.domain.entity.market.NftDetailItem
import java.math.BigInteger

data class NftDetailItemResponse(
    @SerializedName("nftImageUrl")
    val nftImageUrl: String,
    @SerializedName("creatorNickname")
    val creatorNickname: String,
    @SerializedName("songTitle")
    val songTitle: String,
    @SerializedName("saleHistoryList")
    val saleHistoryList: List<HistoryItem>,
    @SerializedName("isSelling")
    val isSelling: Boolean,
    @SerializedName("price")
    val price: BigInteger,
    @SerializedName("isLike")
    val isLike: Boolean,
    @SerializedName("sellerNickname")
    val sellerNickname: String,
    @SerializedName("sellerAccount")
    val sellerAccount: String
) : DataToDomainMapper<NftDetailItem> {
    override fun toDomainModel(): NftDetailItem =
        NftDetailItem(nftImageUrl, creatorNickname, songTitle, saleHistoryList, isSelling, price, isLike, sellerNickname, sellerAccount)
}