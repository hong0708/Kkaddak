package com.ssafy.kkaddak.data.local.datasource

import android.content.Context

class SharedPreferences(context: Context) {

    private val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    fun clearPreferences() {
        prefs.edit().clear().apply()
    }
}