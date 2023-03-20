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
class ApplicationClass : Application() {

    override fun onCreate() {
        super.onCreate()
        preferences = SharedPreferences(applicationContext)
        KakaoSdk.init(this, getString(R.string.KAKAO_NATIVE_APP_KEY))
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setupBouncyCastle()
    }

    private fun setupBouncyCastle() {
        val provider: Provider? = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME)
        if (provider == null) {
            // Web3j will set up the provider lazily when it's first used.
            return
        }
        if (provider.javaClass == BouncyCastleProvider::class.java) {
            // BC with same package name, shouldn't happen in real life.
            return
        }
        // Android registers its own BC provider. As it might be outdated and might not include
        // all needed ciphers, we substitute it with a known BC bundled in the app.
        // Android's BC has its package rewritten to "com.android.org.bouncycastle" and because
        // of that it's possible to have another BC implementation loaded in VM.
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME)
        Security.insertProviderAt(BouncyCastleProvider(), 1)
    }

    companion object {
        lateinit var preferences: SharedPreferences
    }
}