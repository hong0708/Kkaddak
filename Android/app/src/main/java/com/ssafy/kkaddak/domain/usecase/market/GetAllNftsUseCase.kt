package com.ssafy.kkaddak.domain.usecase.market

import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.market.NftItem
import com.ssafy.kkaddak.domain.repository.MarketRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllNftsUseCase @Inject constructor(
    private val marketRepository: MarketRepository
){
    suspend operator fun invoke(lastId: Long, limit: Long): Resource<List<NftItem>> =
        withContext(Dispatchers.IO) {
            marketRepository.getAllNfts(lastId, limit)
        }
}