package com.ssafy.kkaddak.data.remote.datasource.market

import com.ssafy.kkaddak.data.remote.service.MarketApiService
import javax.inject.Inject

class MarketRemoteDataSourceImpl @Inject constructor(
    private val marketApiService: MarketApiService
) : MarketRemoteDataSource {

    override suspend fun getAllNfts(lastId: Int, limit: Int, onlySelling: Boolean): List<NftItemResponse> =
        marketApiService.getAllNfts(lastId, limit, onlySelling).data!!

    override suspend fun getBookmarks(lastId: Int, limit: Int, onlySelling: Boolean): List<NftItemResponse> =
        marketApiService.getBookmarks(lastId, limit, onlySelling).data!!

    override suspend fun requestMarketBookmark(marketId: Int): Boolean =
        marketApiService.requestMarketBookmark(marketId).data!!

    override suspend fun cancelMarketBookmark(marketId: Int): Boolean =
        marketApiService.cancelMarketBookmark(marketId).data!!
}
