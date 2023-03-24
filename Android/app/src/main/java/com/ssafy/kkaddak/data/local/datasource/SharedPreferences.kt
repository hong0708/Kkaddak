package com.ssafy.kkaddak.data.local.datasource

import android.content.Context

class SharedPreferences(context: Context) {

    private val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    var accessToken: String?
        get() = prefs.getString("accessToken", null)
        set(value) = prefs.edit().putString("accessToken", value).apply()

    var refreshToken: String?
        get() = prefs.getString("refreshToken", null)
        set(value) = prefs.edit().putString("refreshToken", value).apply()

    var isLoggedIn: Boolean
        get() = prefs.getBoolean("isLoggedIn", false)
        set(value) = prefs.edit().putBoolean("isLoggedIn", value).apply()

    var nickname: String?
        get() = prefs.getString("nickname", null)
        set(value) = prefs.edit().putString("nickname", value).apply()

    var walletAddress: String?
        get() = prefs.getString("walletAddress", "")
        set(value) = prefs.edit().putString("walletAddress", value).apply()

    var privateKey: String?
        get() = prefs.getString("privateKey", "")
        set(value) = prefs.edit().putString("privateKey", value).apply()

    fun clearPreferences() {
        prefs.edit().clear().apply()
    }
}