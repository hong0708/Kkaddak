package com.ssafy.kkaddak.data.remote.datasource.market

interface MarketRemoteDataSource {

    suspend fun getAllNfts(lastId: Int, limit: Int, onlySelling: Boolean): List<NftItemResponse>

    suspend fun requestMarketBookmark(marketId: Int): Boolean

    suspend fun cancelMarketBookmark(marketId: Int): Boolean
}
