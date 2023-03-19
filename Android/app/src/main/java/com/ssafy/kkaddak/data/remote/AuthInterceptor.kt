package com.ssafy.kkaddak.data.remote

import com.ssafy.kkaddak.data.local.datasource.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val preferences: SharedPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "bearer " + preferences.accessToken).build()

        return chain.proceed(request)
    }
}