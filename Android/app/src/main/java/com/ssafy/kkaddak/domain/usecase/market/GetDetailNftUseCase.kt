package com.ssafy.kkaddak.domain.usecase.market

import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.market.NftDetailItem
import com.ssafy.kkaddak.domain.repository.MarketRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetDetailNftUseCase @Inject constructor(
    private val marketRepository: MarketRepository
){
    suspend operator fun invoke(marketId: Int): Resource<NftDetailItem> =
        withContext(Dispatchers.IO) {
            marketRepository.getDetailNft(marketId)
        }
}