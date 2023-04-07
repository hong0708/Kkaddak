package com.ssafy.kkaddak.data.remote.datasource.market

interface MarketRemoteDataSource {

    suspend fun getAllNfts(lastId: Int, limit: Int, onlySelling: Boolean): List<NftItemResponse>

    suspend fun getBookmarks(lastId: Int, limit: Int, onlySelling: Boolean): List<NftItemResponse>

    suspend fun uploadNft(creatorName: String, nftId: String, nftImagePath: String, price: Double, songTitle: String): UploadNftItemResponse

    suspend fun getDetailNft(marketId: Int): NftDetailItemResponse

    suspend fun requestMarketBookmark(marketId: Int): Boolean

    suspend fun cancelMarketBookmark(marketId: Int): Boolean

    suspend fun closeMarket(marketId: Int): Boolean
}