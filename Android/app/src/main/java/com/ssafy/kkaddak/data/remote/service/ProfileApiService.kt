package com.ssafy.kkaddak.data.remote.service

import com.ssafy.kkaddak.data.remote.datasource.base.BaseResponse
import com.ssafy.kkaddak.data.remote.datasource.profile.FollowerResponse
import com.ssafy.kkaddak.data.remote.datasource.profile.ProfileResponse
import com.ssafy.kkaddak.data.remote.datasource.song.SongResponse
import retrofit2.http.*

interface ProfileApiService {

    @GET("/api/v1/members/profile/{nickname}")
    suspend fun getProfileInfo(@Path("nickname") nickname: String): BaseResponse<ProfileResponse>

    @GET("/api/v2/song/profile/{nickname}")
    suspend fun getProfileSong(@Path("nickname") nickname: String): BaseResponse<List<SongResponse>>

    @DELETE("/api/v2/song/delete/{songId}")
    suspend fun deleteMySong(@Path("songId") songId: String)

    @POST("/api/v1/members/follow/{artistId}")
    suspend fun followArtist(@Path("artistId") artistId: String)

    @POST("/api/v1/members/unfollow/{artistId}")
    suspend fun unfollowArtist(@Path("artistId") artistId: String)

    @GET("/api/v1/members/my-followers")
    suspend fun getFollowers(
        @Query("lastId") lastId: Int,
        @Query("limit") limit: Int
    ): BaseResponse<List<FollowerResponse>>

    @GET("/api/v1/members/my-followings")
    suspend fun getFollowings(
        @Query("lastId") lastId: Int,
        @Query("limit") limit: Int
    ): BaseResponse<List<FollowerResponse>>
}