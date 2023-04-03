package com.ssafy.kkaddak.domain.usecase.market

import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.market.UploadNftItem
import com.ssafy.kkaddak.domain.repository.MarketRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UploadNftUseCase @Inject constructor(
    private val marketRepository: MarketRepository
) {
    suspend operator fun invoke(
        creatorName: String,
        nftId: String,
        nftImagePath: String,
        price: Double,
        songTitle: String
    ): Resource<UploadNftItem> =
        withContext(Dispatchers.IO) {
            marketRepository.uploadNft(creatorName, nftId, nftImagePath, price, songTitle)
        }
}