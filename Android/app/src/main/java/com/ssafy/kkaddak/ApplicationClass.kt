package com.ssafy.kkaddak

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.kakao.sdk.common.KakaoSdk
import com.ssafy.kkaddak.data.local.datasource.SharedPreferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationClass: Application() {

    override fun onCreate() {
        super.onCreate()
        preferences = SharedPreferences(applicationContext)
        KakaoSdk.init(this, getString(R.string.KAKAO_NATIVE_APP_KEY))
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    companion object {
        lateinit var preferences: SharedPreferences
    }
}