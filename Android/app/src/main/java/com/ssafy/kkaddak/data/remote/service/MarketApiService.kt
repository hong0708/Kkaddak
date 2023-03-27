package com.ssafy.kkaddak.data.remote.service

import com.ssafy.kkaddak.data.remote.datasource.base.BaseResponse
import com.ssafy.kkaddak.data.remote.datasource.market.NftItemResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface MarketApiService {

    @GET("/api/v3/market/condition")
    suspend fun getAllNfts(
        @Query("lastId") lastId: Int,
        @Query("limit") limit: Int,
        @Query("onlySelling") onlySelling: Boolean
    ) : BaseResponse<List<NftItemResponse>>

    @POST("/api/v3/market/like/{marketId}")
    suspend fun requestMarketBookmark(@Path("marketId") marketId: Int): BaseResponse<Boolean>

    @POST("/api/v3/market/unlike/{marketId}")
    suspend fun cancelMarketBookmark(@Path("marketId") marketId: Int): BaseResponse<Boolean>

}