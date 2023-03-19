package com.ssafy.kkaddak.di

import com.ssafy.kkaddak.data.remote.datasource.auth.AuthRemoteDataSourceImpl
import com.ssafy.kkaddak.data.remote.datasource.user.UserRemoteDataSourceImpl
import com.ssafy.kkaddak.data.remote.service.AuthApiService
import com.ssafy.kkaddak.data.remote.service.RefreshApiService
import com.ssafy.kkaddak.data.remote.service.UserApiService
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

}