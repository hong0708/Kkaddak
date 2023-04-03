package com.ssafy.kkaddak.domain.usecase.user

import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChargeCoinUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(receiptId: String): Resource<Boolean> =
        withContext(Dispatchers.IO) {
            userRepository.chargeCoin(receiptId)
        }
}