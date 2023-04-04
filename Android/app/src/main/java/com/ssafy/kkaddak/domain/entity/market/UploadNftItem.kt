package com.ssafy.kkaddak.domain.entity.market

class UploadNftItem(
    val marketId: Int,
    var nftId: String,
    var nftCreator: String?,
    var nftSongTitle: String?,
    var nftImagePath: String?,
    val createAt: String,
    var nftPrice: Double,
    val isClose: Boolean
)