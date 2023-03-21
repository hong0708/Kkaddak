package com.ssafy.kkaddak.domain.repository

import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.profile.ProfileItem

interface ProfileRepository {

    suspend fun getProfileInfo(nickname: String): Resource<ProfileItem>
}