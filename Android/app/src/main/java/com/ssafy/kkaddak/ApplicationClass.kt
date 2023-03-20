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


    /*if (provider == null) 문장 삭제
    null 체크를 하지 않아도 provider?.javaClass 로 안전하게 체크할 수 있습니다.
    if (provider.javaClass == BouncyCastleProvider::class.java) 문장 변경
    Kotlin의 null safe operator ?. 와 != 연산자를 사용하여 더 간결하게 표현하였습니다.
    주석 추가 및 변경
    주석을 더 명확하게 작성하고, 필요없는 주석은 삭제하였습니다.
    BouncyCastleProvider를 등록하는 부분 변경
    provider가 null이 아니면서 BouncyCastleProvider가 아니면 Security에 BouncyCastleProvider를 등록합니다.*/

    private fun setupBouncyCastle() {
        val provider: Provider? = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME)
        if (provider?.javaClass != BouncyCastleProvider::class.java) {
            // Register BouncyCastleProvider if it's not already registered
            Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME)
            Security.insertProviderAt(BouncyCastleProvider(), 1)
        }
    }

    private fun setupBouncyCastle1() {
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