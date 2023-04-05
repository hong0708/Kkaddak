package com.ssafy.kkaddak.data.remote.service

import com.ssafy.kkaddak.data.remote.datasource.base.BaseResponse
import com.ssafy.kkaddak.data.remote.datasource.market.NftDetailItemResponse
import com.ssafy.kkaddak.data.remote.datasource.market.NftItemResponse
import com.ssafy.kkaddak.data.remote.datasource.market.UploadNftItemRequest
import com.ssafy.kkaddak.data.remote.datasource.market.UploadNftItemResponse
import retrofit2.http.*

interface MarketApiService {

    @GET("/api/v3/market/condition")
    suspend fun getAllNfts(
        @Query("lastId") lastId: Int,
        @Query("limit") limit: Int,
        @Query("onlySelling") onlySelling: Boolean
    ): BaseResponse<List<NftItemResponse>>

    @GET("/api/v3/market/my-like")
    suspend fun getBookmarks(
        @Query("lastId") lastId: Int,
        @Query("limit") limit: Int,
        @Query("onlySelling") onlySelling: Boolean
    ): BaseResponse<List<NftItemResponse>>

    @POST("/api/v3/market/create")
    suspend fun uploadNft(@Body body: UploadNftItemRequest): BaseResponse<UploadNftItemResponse>

    @GET("/api/v3/market/detail/{marketId}")
    suspend fun getDetailNft(@Path("marketId") marketId: Int): BaseResponse<NftDetailItemResponse>

    @POST("/api/v3/market/like/{marketId}")
    suspend fun requestMarketBookmark(@Path("marketId") marketId: Int): BaseResponse<Boolean>

    @POST("/api/v3/market/unlike/{marketId}")
    suspend fun cancelMarketBookmark(@Path("marketId") marketId: Int): BaseResponse<Boolean>

    @POST("/api/v3/market/close")
    suspend fun closeMarket(@Body marketId: Int): BaseResponse<Boolean>
}