package com.ssafy.kkaddak.domain.repository

import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.market.NftDetailItem
import com.ssafy.kkaddak.domain.entity.market.NftItem
import com.ssafy.kkaddak.domain.entity.market.UploadNftItem

interface MarketRepository {

    suspend fun getAllNfts(lastId: Int, limit: Int, onlySelling: Boolean): Resource<List<NftItem>>

    suspend fun getBookmarks(lastId: Int, limit: Int, onlySelling: Boolean): Resource<List<NftItem>>

    suspend fun uploadNft(creatorName: String, nftId: String, nftImagePath: String, price: Double, songTitle: String) : Resource<UploadNftItem>

    suspend fun getDetailNft(marketId: Int): Resource<NftDetailItem>

    suspend fun requestMarketBookmark(marketId: Int): Resource<Boolean>

    suspend fun cancelMarketBookmark(marketId: Int): Resource<Boolean>
}