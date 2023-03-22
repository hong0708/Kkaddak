package com.ssafy.kkaddak.domain.repository

import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.market.NftItem

interface MarketRepository {

    suspend fun getAllNfts(lastId: Long, limit: Long) : Resource<List<NftItem>>
}