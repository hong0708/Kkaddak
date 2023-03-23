package com.ssafy.kkaddak.data.remote.datasource.market

interface MarketRemoteDataSource {

    suspend fun getAllNfts(lastId: Long, limit: Long, onlySelling: Boolean) : List<NftItemResponse>
}
