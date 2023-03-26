package com.ssafy.kkaddak.domain.repository

import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.market.NftItem

interface MarketRepository {

    suspend fun getAllNfts(lastId: Int, limit: Int, onlySelling: Boolean): Resource<List<NftItem>>

    suspend fun requestMarketBookmark(auctionId: Int): Resource<Boolean>

    suspend fun cancelMarketBookmark(auctionId: Int): Resource<Boolean>
}