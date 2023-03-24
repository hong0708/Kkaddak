package com.ssafy.kkaddak

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.kakao.sdk.common.KakaoSdk
import com.ssafy.kkaddak.data.local.datasource.SharedPreferences
import dagger.hilt.android.HiltAndroidApp
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.Provider
import java.security.Security

@HiltAndroidApp
class ApplicationClass: Application() {

    override fun onCreate() {
        super.onCreate()
        preferences = SharedPreferences(applicationContext)
        KakaoSdk.init(this, getString(R.string.KAKAO_NATIVE_APP_KEY))
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setupBouncyCastle()
    }

    // BouncyCastleProvider가 아니면 Security에 BouncyCastleProvider를 등록
    private fun setupBouncyCastle() {
        val provider: Provider? = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME)
        if (provider?.javaClass != BouncyCastleProvider::class.java) {
            // Register BouncyCastleProvider if it's not already registered
            Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME)
            Security.insertProviderAt(BouncyCastleProvider(), 1)
        }
    }

    companion object {
        lateinit var preferences: SharedPreferences
    }
}