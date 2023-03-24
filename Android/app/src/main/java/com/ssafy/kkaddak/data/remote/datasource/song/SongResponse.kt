package com.ssafy.kkaddak.data.remote.datasource.song

import com.google.gson.annotations.SerializedName
import com.ssafy.kkaddak.data.remote.datasource.base.DataToDomainMapper
import com.ssafy.kkaddak.domain.entity.song.SongItem

data class SongResponse(
    @SerializedName("songId")
    val songId: String,
    @SerializedName("songTitle")
    val songTitle: String,
    @SerializedName("songPath")
    val songPath: String,
    @SerializedName("coverPath")
    val coverPath: String,
    @SerializedName("genre")
    val genre: String,
    @SerializedName("mood")
    val mood: List<String>?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("like")
    val like: Boolean,
    @SerializedName("combination")
    val combination: List<Int>?,
    @SerializedName("songStatus")
    val songStatus: String?
) : DataToDomainMapper<SongItem> {
    override fun toDomainModel(): SongItem =
        SongItem(songId, songTitle, songPath, coverPath, genre, mood, nickname, like, combination, songStatus)
}