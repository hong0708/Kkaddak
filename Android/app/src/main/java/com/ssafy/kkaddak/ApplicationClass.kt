package com.ssafy.kkaddak

import android.app.Application
import com.ssafy.kkaddak.data.local.datasource.SharedPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationClass: Application() {

    override fun onCreate() {
        super.onCreate()
        preferences = SharedPreferences(applicationContext)
    }

    companion object {
        lateinit var preferences: SharedPreferences
    }
}