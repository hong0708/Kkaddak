package com.ssafy.kkaddak.data.remote.datasource.market

import com.ssafy.kkaddak.data.remote.service.MarketApiService
import javax.inject.Inject

class MarketRemoteDataSourceImpl @Inject constructor(
    private val marketApiService: MarketApiService
) : MarketRemoteDataSource {

    override suspend fun getAllNfts(lastId: Long, limit: Long): List<NftItemResponse> =
        marketApiService.getAllNfts(lastId, limit).data!!
}
