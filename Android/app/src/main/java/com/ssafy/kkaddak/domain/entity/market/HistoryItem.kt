package com.ssafy.kkaddak.domain.entity.market

import java.math.BigInteger

data class HistoryItem(
    val price: BigInteger,
    val timestamp: String
)