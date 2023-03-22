package com.ssafy.kkaddak.di

import com.ssafy.kkaddak.data.remote.datasource.auth.AuthRemoteDataSourceImpl
import com.ssafy.kkaddak.data.remote.datasource.home.HomeRemoteDataSourceImpl
import com.ssafy.kkaddak.data.remote.datasource.profile.ProfileRemoteDataSourceImpl
import com.ssafy.kkaddak.data.remote.datasource.song.SongRemoteDataSourceImpl
import com.ssafy.kkaddak.data.remote.datasource.user.UserRemoteDataSourceImpl
import com.ssafy.kkaddak.data.remote.repository.*
import com.ssafy.kkaddak.domain.repository.*
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
    fun provideProfileRepository(
        profileRemoteDataSourceImpl: ProfileRemoteDataSourceImpl
    ): ProfileRepository = ProfileRepositoryImpl(profileRemoteDataSourceImpl)

    @Provides
    @Singleton
    fun provideHomeRepository(
        homeRemoteDataSourceImpl: HomeRemoteDataSourceImpl
    ): HomeRepository = HomeRepositoryImpl(homeRemoteDataSourceImpl)
}