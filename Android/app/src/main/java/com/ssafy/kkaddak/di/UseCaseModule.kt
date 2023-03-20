package com.ssafy.kkaddak.di

import com.ssafy.kkaddak.domain.repository.AuthRepository
import com.ssafy.kkaddak.domain.repository.SongRepository
import com.ssafy.kkaddak.domain.repository.UserRepository
import com.ssafy.kkaddak.domain.usecase.auth.GetNewTokenUseCase
import com.ssafy.kkaddak.domain.usecase.auth.LoginUseCase
import com.ssafy.kkaddak.domain.usecase.song.GetPlayListUseCase
import com.ssafy.kkaddak.domain.usecase.song.GetSongDetailUseCase
import com.ssafy.kkaddak.domain.usecase.song.GetSongsUseCase
import com.ssafy.kkaddak.domain.usecase.song.RequestBookmarkUseCase
import com.ssafy.kkaddak.domain.usecase.user.CheckDuplicationUseCase
import com.ssafy.kkaddak.domain.usecase.user.CreateUserInfoUseCase
import com.ssafy.kkaddak.domain.usecase.user.RequestCancelSignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {
    @Singleton
    @Provides
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase =
        LoginUseCase(authRepository)

    @Singleton
    @Provides
    fun provideGetNewTokenUseCase(authRepository: AuthRepository): GetNewTokenUseCase =
        GetNewTokenUseCase(authRepository)

    @Singleton
    @Provides
    fun provideCreateUserInfoUseCase(userRepository: UserRepository): CreateUserInfoUseCase =
        CreateUserInfoUseCase(userRepository)

    @Singleton
    @Provides
    fun provideCheckDuplicationUseCase(userRepository: UserRepository): CheckDuplicationUseCase =
        CheckDuplicationUseCase(userRepository)

    @Singleton
    @Provides
    fun provideRequestCancelSignUpUseCase(userRepository: UserRepository): RequestCancelSignUpUseCase =
        RequestCancelSignUpUseCase(userRepository)
    @Singleton
    @Provides
    fun provideGetSongsUseCase(songRepository: SongRepository): GetSongsUseCase =
        GetSongsUseCase(songRepository)

    @Singleton
    @Provides
    fun provideRequestBookmarkUseCase(songRepository: SongRepository): RequestBookmarkUseCase =
        RequestBookmarkUseCase(songRepository)

    @Singleton
    @Provides
    fun provideGetSongDetailUseCase(songRepository: SongRepository): GetSongDetailUseCase =
        GetSongDetailUseCase(songRepository)

    @Singleton
    @Provides
    fun provideGetPlayListUseCase(songRepository: SongRepository): GetPlayListUseCase =
        GetPlayListUseCase(songRepository)
}