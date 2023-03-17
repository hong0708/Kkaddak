package com.ssafy.kkaddak.common.util

import com.ssafy.kkaddak.data.remote.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> wrapToResource(apiCall: suspend () -> T): Resource<T> {
    return withContext(Dispatchers.IO) {
        try {
            Resource.Success(apiCall())
        } catch (throwable: Throwable) {
            when(throwable) {
                is IOException -> Resource.Error(throwable.message ?: "ERROR")
                is HttpException -> {
                    val code = throwable.code()
                    Resource.Error(code.toString())
                }
                else -> {
                    Resource.Error(throwable.message ?: "ERROR")
                }
            }
        }
    }
}