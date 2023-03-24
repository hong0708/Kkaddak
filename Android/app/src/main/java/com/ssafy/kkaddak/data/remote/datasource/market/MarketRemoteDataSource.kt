package com.ssafy.kkaddak.data.remote.datasource.market

interface MarketRemoteDataSource {

    suspend fun getAllNfts(lastId: Int, limit: Int, onlySelling: Boolean) : List<NftItemResponse>

    suspend fun requestMarketBookmark(auctionId: Int)

    suspend fun cancelMarketBookmark(auctionId: Int)
}
