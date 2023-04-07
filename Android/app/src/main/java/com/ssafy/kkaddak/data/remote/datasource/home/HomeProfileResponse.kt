package com.ssafy.kkaddak.data.remote.datasource.home

import com.google.gson.annotations.SerializedName
import com.ssafy.kkaddak.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.kkaddak.domain.entity.home.HomeProfile

data class HomeProfileResponse(
    @SerializedName("memberId")
    val memberId: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("profilepath")
    val profilepath: String?,
    @SerializedName("mySongs")
    val mySongs: Int,
    @SerializedName("nftThumbnailUrl")
    val nftThumbnailUrl: String?
) : DataToDomainMapper<HomeProfile> {
    override fun toDomainModel(): HomeProfile =
        HomeProfile(memberId, nickname, profilepath, mySongs, nftThumbnailUrl)
}