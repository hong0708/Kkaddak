package com.ssafy.kkaddak.data.remote.repository

import com.ssafy.kkaddak.common.util.wrapToResource
import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.data.remote.datasource.market.MarketRemoteDataSource
import com.ssafy.kkaddak.domain.entity.market.NftItem
import com.ssafy.kkaddak.domain.repository.MarketRepository
import javax.inject.Inject

class MarketRepositoryImpl @Inject constructor(
    private val marketRemoteDataSource: MarketRemoteDataSource
) : MarketRepository {

    override suspend fun getAllNfts(
        lastId: Int,
        limit: Int,
        onlySelling: Boolean
    ): Resource<List<NftItem>> =
        wrapToResource {
            marketRemoteDataSource.getAllNfts(lastId, limit, onlySelling).map { it.toDomainModel() }
        }

    override suspend fun requestMarketBookmark(auctionId: Int): Resource<Boolean> =
        wrapToResource {
            marketRemoteDataSource.requestMarketBookmark(auctionId)
        }

    override suspend fun cancelMarketBookmark(auctionId: Int): Resource<Boolean> =
        wrapToResource {
            marketRemoteDataSource.cancelMarketBookmark(auctionId)
        }
}