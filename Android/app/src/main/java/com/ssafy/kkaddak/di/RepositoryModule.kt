package com.ssafy.kkaddak.di

import com.ssafy.kkaddak.data.remote.datasource.auth.AuthRemoteDataSourceImpl
import com.ssafy.kkaddak.data.remote.datasource.market.MarketRemoteDataSourceImpl
import com.ssafy.kkaddak.data.remote.datasource.song.SongRemoteDataSourceImpl
import com.ssafy.kkaddak.data.remote.datasource.user.UserRemoteDataSourceImpl
import com.ssafy.kkaddak.data.remote.repository.AuthRepositoryImpl
import com.ssafy.kkaddak.data.remote.repository.MarketRepositoryImpl
import com.ssafy.kkaddak.data.remote.repository.SongRepositoryImpl
import com.ssafy.kkaddak.data.remote.repository.UserRepositoryImpl
import com.ssafy.kkaddak.domain.repository.AuthRepository
import com.ssafy.kkaddak.domain.repository.MarketRepository
import com.ssafy.kkaddak.domain.repository.SongRepository
import com.ssafy.kkaddak.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        authRemoteDataSourceImpl: AuthRemoteDataSourceImpl
    ): AuthRepository = AuthRepositoryImpl(authRemoteDataSourceImpl)

    @Provides
    @Singleton
    fun provideUserRepository(
        userRemoteDataSourceImpl: UserRemoteDataSourceImpl
    ): UserRepository = UserRepositoryImpl(userRemoteDataSourceImpl)

    @Provides
    @Singleton
    fun provideSongRepository(
        songRemoteDataSourceImpl: SongRemoteDataSourceImpl
    ): SongRepository = SongRepositoryImpl(songRemoteDataSourceImpl)

    @Provides
    @Singleton
    fun provideMarketRepository(
        marketRemoteDataSourceImpl: MarketRemoteDataSourceImpl
    ): MarketRepository = MarketRepositoryImpl(marketRemoteDataSourceImpl)
}