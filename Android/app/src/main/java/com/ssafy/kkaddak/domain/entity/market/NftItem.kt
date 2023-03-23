package com.ssafy.kkaddak.domain.entity.market

class NftItem(
    val acutionId: Int,
    val nftId: String,
    val nftCreator: String,
    val nftSingTitle: String,
    val nftImagePath: String,
    val nftDeadline: String,
    val nftPrice: Double,
    val isClosed: Boolean,
    val cntLikeAcution: Int,
    val isLike: Boolean
)