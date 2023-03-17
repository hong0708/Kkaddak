package com.ssafy.kkaddak.data.remote.datasource.song

import com.google.gson.annotations.SerializedName
import com.ssafy.kkaddak.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.kkaddak.domain.entity.SongItem

data class SongResponse(
    @SerializedName("track")
    val track: String,
    @SerializedName("streamUrl")
    val streamUrl: String,
    @SerializedName("artist")
    val artist: String,
    @SerializedName("coverUrl")
    val coverUrl: String
) : DataToDomainMapper<SongItem> {
    fun toDomainModel(id: Long): SongItem =
        SongItem(id = id, track, streamUrl, artist, coverUrl)

    override fun toDomainModel(): SongItem =
        SongItem(1, track, streamUrl, artist, coverUrl)
}