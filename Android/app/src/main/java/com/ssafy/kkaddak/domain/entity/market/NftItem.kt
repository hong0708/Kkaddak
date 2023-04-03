package com.ssafy.kkaddak.domain.entity.market

data class NftItem(
    val marketId: Int,
    val nftId: String,
    var nftCreator: String,
    val nftSongTitle: String,
    var nftImagePath: String,
    val nftCreateDate: String,
    var nftPrice: Double,
    val isClose: Boolean,
    val cntLikeMarket: Int,
    var isLike: Boolean
)