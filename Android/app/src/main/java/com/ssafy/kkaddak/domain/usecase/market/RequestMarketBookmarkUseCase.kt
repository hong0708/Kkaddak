package com.ssafy.kkaddak.domain.usecase.market

import com.ssafy.kkaddak.domain.repository.MarketRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestMarketBookmarkUseCase @Inject constructor(
    private val marketRepository: MarketRepository
){
    suspend operator fun invoke(auctionId: Int) {
        withContext(Dispatchers.IO) {
            marketRepository.requestMarketBookmark(auctionId)
        }
    }
}