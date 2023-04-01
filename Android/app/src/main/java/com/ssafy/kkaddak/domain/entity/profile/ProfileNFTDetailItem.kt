package com.ssafy.kkaddak.domain.entity.profile

import java.math.BigInteger

data class ProfileNFTDetailItem(
    var nftImageUrl: String?,
    var coverImageUrl: String?,
    var creatorNickname: String?,
    var createdDate: BigInteger?,
    var trackTitle: String?,
    var combination: String?
)
