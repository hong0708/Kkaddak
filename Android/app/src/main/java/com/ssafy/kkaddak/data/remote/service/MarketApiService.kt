package com.ssafy.kkaddak.data.remote.service

import com.ssafy.kkaddak.data.remote.datasource.base.BaseResponse
import com.ssafy.kkaddak.data.remote.datasource.market.NftItemResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MarketApiService {

    @GET("/api/v3/auction/condition")
    suspend fun getAllNfts(
        @Query("lastId") lastId: Long,
        @Query("limit") limit: Long,
        @Query("onlySelling") onlySelling: Boolean
    ) : BaseResponse<List<NftItemResponse>>

}