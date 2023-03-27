package com.ssafy.kkaddak.data.remote.repository

import com.ssafy.kkaddak.common.util.wrapToResource
import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.data.remote.datasource.user.UserRemoteDataSource
import com.ssafy.kkaddak.domain.entity.user.User
import com.ssafy.kkaddak.domain.repository.UserRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun createUserInfo(
        nickName: String,
        profileImg: MultipartBody.Part?
    ): Resource<User> =
        wrapToResource {
            userRemoteDataSource.createUserInfo(nickName, profileImg).toDomainModel()
        }

    override suspend fun createUserInfoWithoutImg(
        nickName: String
    ): Resource<User> =
        wrapToResource {
            userRemoteDataSource.createUserInfoWithoutImg(nickName).toDomainModel()
        }

    override suspend fun checkDuplication(nickName: String): Resource<Boolean> =
        wrapToResource {
            userRemoteDataSource.checkDuplication(nickName)
        }

    override suspend fun cancelSignUp(): Resource<Boolean> =
        wrapToResource {
            userRemoteDataSource.cancelSignUp()
        }

    override suspend fun getUserProfile(): Resource<String?> =
        wrapToResource {
            userRemoteDataSource.getUserProfileImg()
        }

    override suspend fun logout(atk: String, rtk: String): Resource<Boolean> =
        wrapToResource {
            userRemoteDataSource.logout(atk, rtk)
        }
}