package com.ssafy.kkaddak.di

import com.ssafy.kkaddak.data.remote.datasource.auth.AuthRemoteDataSourceImpl
import com.ssafy.kkaddak.data.remote.datasource.home.HomeRemoteDataSourceImpl
import com.ssafy.kkaddak.data.remote.datasource.market.MarketRemoteDataSourceImpl
import com.ssafy.kkaddak.data.remote.datasource.profile.ProfileRemoteDataSourceImpl
import com.ssafy.kkaddak.data.remote.datasource.song.SongRemoteDataSourceImpl
import com.ssafy.kkaddak.data.remote.datasource.user.UserRemoteDataSourceImpl
import com.ssafy.kkaddak.data.remote.service.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideAuthDataSource(
        authApiService: AuthApiService,
        refreshApiService: RefreshApiService
    ): AuthRemoteDataSourceImpl = AuthRemoteDataSourceImpl(authApiService, refreshApiService)

    @Provides
    @Singleton
    fun provideUserDataSource(
        userApiService: UserApiService
    ): UserRemoteDataSourceImpl = UserRemoteDataSourceImpl(userApiService)

    @Provides
    @Singleton
    fun provideSongDataSource(
        songApiService: SongApiService
    ): SongRemoteDataSourceImpl = SongRemoteDataSourceImpl(songApiService)

    @Provides
    @Singleton
    fun provideMarketDataSource(
        marketApiService: MarketApiService
    ): MarketRemoteDataSourceImpl = MarketRemoteDataSourceImpl(marketApiService)

    @Provides
    @Singleton
    fun provideProfileDataSource(
        profileApiService: ProfileApiService
    ): ProfileRemoteDataSourceImpl = ProfileRemoteDataSourceImpl(profileApiService)

    @Provides
    @Singleton
    fun provideHomeDataSource(
        homeApiService: HomeApiService
    ): HomeRemoteDataSourceImpl = HomeRemoteDataSourceImpl(homeApiService)
}