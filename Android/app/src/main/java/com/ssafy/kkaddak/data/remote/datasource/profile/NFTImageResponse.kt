package com.ssafy.kkaddak.data.remote.datasource.profile

import com.google.gson.annotations.SerializedName
import com.ssafy.kkaddak.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.kkaddak.domain.entity.profile.NFTImageItem

data class NFTImageResponse(
    @SerializedName("nftImageUrl")
    val nftImageUrl: String
) : DataToDomainMapper<NFTImageItem> {
    override fun toDomainModel(): NFTImageItem =
        NFTImageItem(nftImageUrl)
}
