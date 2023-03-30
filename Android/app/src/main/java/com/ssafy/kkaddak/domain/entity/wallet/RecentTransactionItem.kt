package com.ssafy.kkaddak.domain.entity.wallet

import java.math.BigInteger

data class RecentTransactionItem(
    val sender: String,
    val recipient: String,
    val timestamp: BigInteger,
    val amount: BigInteger,
    val transferType: String
)