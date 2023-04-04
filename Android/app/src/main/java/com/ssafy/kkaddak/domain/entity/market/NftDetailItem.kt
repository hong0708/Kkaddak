package com.ssafy.kkaddak.domain.entity.market

data class NftDetailItem(
    val nftImageUrl: String,
    val creatorNickname: String,
    val songTitle: String,
    val saleHistoryList: List<HistoryItem>,
    val isSelling: Boolean,
    val price: Double,
    var isLike: Boolean,
    val sellerNickname: String,
    val sellerAccount: String
)