package com.ssafy.kkaddak.domain.usecase.user

import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckDuplicationUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(nickname: String): Resource<Boolean> = withContext(Dispatchers.IO) {
        userRepository.checkDuplication(nickname)
    }
}