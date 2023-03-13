package com.ssafy.kkaddak.di

import com.ssafy.kkaddak.data.remote.datasource.auth.AuthRemoteDataSourceImpl
import com.ssafy.kkaddak.data.remote.datasource.user.UserRemoteDataSourceImpl
import com.ssafy.kkaddak.data.remote.repository.AuthRepositoryImpl
import com.ssafy.kkaddak.data.remote.repository.UserRepositoryImpl
import com.ssafy.kkaddak.domain.repository.AuthRepository
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

}