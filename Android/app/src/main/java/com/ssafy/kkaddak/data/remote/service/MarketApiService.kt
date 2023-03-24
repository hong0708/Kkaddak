package com.ssafy.kkaddak.data.remote.service

import com.ssafy.kkaddak.data.remote.datasource.base.BaseResponse
import com.ssafy.kkaddak.data.remote.datasource.market.NftItemResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface MarketApiService {

    @GET("/api/v3/auction/condition")
    suspend fun getAllNfts(
        @Query("lastId") lastId: Int,
        @Query("limit") limit: Int,
        @Query("onlySelling") onlySelling: Boolean
    ) : BaseResponse<List<NftItemResponse>>

    @POST("/api/v3/auction/like/{auctionId}")
    suspend fun requestMarketBookmark(@Path("auctionId") auctionId: Int)

    @POST("/api/v3/auction/unlike/{auctionId}")
    suspend fun cancelMarketBookmark(@Path("auctionId") auctionId: Int)

}