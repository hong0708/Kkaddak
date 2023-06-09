package com.ssafy.kkaddak.data.remote.repository

import com.ssafy.kkaddak.common.util.wrapToResource
import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.data.remote.datasource.market.MarketRemoteDataSource
import com.ssafy.kkaddak.domain.entity.market.NftDetailItem
import com.ssafy.kkaddak.domain.entity.market.NftItem
import com.ssafy.kkaddak.domain.entity.market.UploadNftItem
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

    override suspend fun getBookmarks(
        lastId: Int,
        limit: Int,
        onlySelling: Boolean
    ): Resource<List<NftItem>> =
        wrapToResource {
            marketRemoteDataSource.getBookmarks(lastId, limit, onlySelling)
                .map { it.toDomainModel() }
        }

    override suspend fun uploadNft(
        creatorName: String,
        nftId: String,
        nftImagePath: String,
        price: Double,
        songTitle: String
    ): Resource<UploadNftItem> =
        wrapToResource {
            marketRemoteDataSource.uploadNft(creatorName, nftId, nftImagePath, price, songTitle)
                .toDomainModel()
        }

    override suspend fun getDetailNft(marketId: Int): Resource<NftDetailItem> =
        wrapToResource {
            marketRemoteDataSource.getDetailNft(marketId).toDomainModel()
        }

    override suspend fun requestMarketBookmark(marketId: Int): Resource<Boolean> =
        wrapToResource {
            marketRemoteDataSource.requestMarketBookmark(marketId)
        }

    override suspend fun cancelMarketBookmark(marketId: Int): Resource<Boolean> =
        wrapToResource {
            marketRemoteDataSource.cancelMarketBookmark(marketId)
        }

    override suspend fun closeMarket(marketId: Int): Resource<Boolean> =
        wrapToResource {
            marketRemoteDataSource.closeMarket(marketId)
        }
}