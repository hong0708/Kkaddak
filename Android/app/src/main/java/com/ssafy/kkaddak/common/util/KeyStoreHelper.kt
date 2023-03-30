package com.ssafy.kkaddak.common.util

import android.content.Context
import android.security.KeyPairGeneratorSpec
import android.util.Log
import java.math.BigInteger
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.security.PublicKey
import java.util.Calendar
import javax.crypto.Cipher
import javax.security.auth.x500.X500Principal

/*import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import androidx.biometric.BiometricManager*/



class KeyStoreHelper(private val context: Context) {
    private val KEYSTORE_PROVIDER = "AndroidKeyStore"
    private val ASYMMETRIC_KEY_ALIAS = "my_key_alias"

    // 클래스 생성자에서 호출되는 함수입니다.
    // createAsymmetricKeyPair() 함수를 호출하여
    // 키스토어에 키 쌍이 존재하지 않는 경우에만 공개키 및 개인키를 생성합니다.

    init {
        // 안드로이드 키스토어에 키 생성
        createAsymmetricKeyPair()
        Log.d("key store info", "key store 생성 ")
    }

    //    키스토어에 공개키 및 개인키 쌍을 생성하는 함수입니다.
    //    isAsymmetricKeyPairExist() 함수를 호출하여 이미 키 쌍이 존재하는지 확인합니다.
    //    키 쌍이 존재하지 않는 경우, RSA 알고리즘을 사용하여 키 쌍을 생성하고,
    //    키의 유효 기간과 관련된 정보를 KeyPairGeneratorSpec에 설정하여 키를 저장합니다.

    private fun createAsymmetricKeyPair() {
        if (!isAsymmetricKeyPairExist()) {

            Log.d("key store info", "key store 키쌍 없어서 생성 ")

            val start = Calendar.getInstance()
            val end = Calendar.getInstance()
            end.add(Calendar.YEAR, 30)
            val spec = KeyPairGeneratorSpec.Builder(context)
                .setAlias(ASYMMETRIC_KEY_ALIAS)
                .setSubject(X500Principal("CN=Sample Name, O=Android Authority"))
                .setSerialNumber(BigInteger.ONE)
                .setStartDate(start.time)
                .setEndDate(end.time)
                .build()
            val keyPairGenerator =
                KeyPairGenerator.getInstance("RSA", KEYSTORE_PROVIDER)
            keyPairGenerator.initialize(spec)
            keyPairGenerator.generateKeyPair()
        }
    }

    //    키스토어에 공개키 및 개인키 쌍이 이미 존재하는지 확인하는 함수입니다.
    //    KeyStore.getInstance()를 사용하여 KEYSTORE_PROVIDER에 해당하는 키스토어를 가져온 후,
    //    해당 키스토어에 ASYMMETRIC_KEY_ALIAS에 해당하는 개인키가 존재하는지 확인합니다.

    private fun isAsymmetricKeyPairExist(): Boolean {

        Log.d("key store info", "key store 키쌍 확인 ")

        val keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER)
        keyStore.load(null)
        val privateKey = keyStore.getKey(ASYMMETRIC_KEY_ALIAS, null)
        return privateKey != null
    }

    // 전달받은 data 바이트 배열을 공개키로 암호화하는 함수입니다.
    // Cipher.getInstance()를 사용하여 RSA 알고리즘 및 PKCS1 패딩을 사용하는 Cipher 객체를 생성합니다.
    // cipher.init()를 사용하여 암호화 모드로 설정한 후, 공개키를 인자로 전달합니다.
    // cipher.doFinal()를 사용하여 데이터를 암호화한 후, 암호화된 데이터를 반환합니다.

    fun encryptData(data: ByteArray): ByteArray {

        Log.d("key store info", "key store 키쌍을 통한 암호화 ")

        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey())

        // 사용되는 블록 크기와 알고리즘 이름을 가져옵니다.
        val blockSize = cipher.blockSize
        val algorithmName = cipher.algorithm

        Log.d("key store info", "암호화 알고리즘: $algorithmName, 블록 크기: $blockSize")
        Log.d("key store info", "암호화한 데이터 : ${cipher.doFinal(data)} ")

        return cipher.doFinal(data)
    }

    // 전달받은 data 바이트 배열을 개인키로 복호화하는 함수입니다.
    // Cipher.getInstance()를 사용하여 RSA 알고리즘 및 PKCS1 패딩을 사용하는 Cipher 객체를 생성합니다.
    // cipher.init()를 사용하여 복호화 모드로 설정한 후, 개인키를 인자로 전달합니다.
    // cipher.doFinal()를 사용하여 데이터를 복호화한 후, 복호화된 데이터를 반환합니다.

    fun decryptData(data: ByteArray): ByteArray {

        Log.d("key store info", "key store 키쌍을 통한 복호화 ")

        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        cipher.init(Cipher.DECRYPT_MODE, getPrivateKey())

        // 사용되는 블록 크기와 알고리즘 이름을 가져옵니다.
        val blockSize = cipher.blockSize
        val algorithmName = cipher.algorithm

        Log.d("key store info", "복호화 알고리즘: $algorithmName, 블록 크기: $blockSize")

        Log.d("key store info", "복호화한 데이터 : ${cipher.doFinal(data)} ")
        Log.d("key store info", "복호화한 데이터 : ${String(cipher.doFinal(data))} ")

        return cipher.doFinal(data)
    }

    // 공개키를 가져오는 함수입니다.
    // KeyStore.getInstance()를 사용하여 KEYSTORE_PROVIDER에 해당하는 키스토어를 가져온 후,
    // ASYMMETRIC_KEY_ALIAS에 해당하는 공개키를 가져옵니다.
    private fun getPublicKey(): PublicKey {
        val keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER)
        keyStore.load(null)
        val publicKey = keyStore.getCertificate(ASYMMETRIC_KEY_ALIAS).publicKey
        return publicKey
    }

    // 개인키를 가져오는 함수입니다.
    private fun getPrivateKey(): PrivateKey {
        val keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER)
        keyStore.load(null)
        val privateKey =
            keyStore.getKey(ASYMMETRIC_KEY_ALIAS, null) as PrivateKey
        return privateKey
    }

    /*fun generateSecretKey(context: Context, keyName: String) {
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(keyName, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            .setUserAuthenticationRequired(true)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            .build()
        keyGenerator.init(keyGenParameterSpec)
        keyGenerator.generateKey()
    }

    fun createBiometricPromptInfo(context: Context): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle("Title")
            .setSubtitle("Subtitle")
            .setDescription("Description")
            .setNegativeButtonText("Cancel")
            .build()
    }

    fun showBiometricPrompt(context: Context, callback: BiometricPrompt.AuthenticationCallback) {
        val biometricManager = BiometricManager.from(context)
        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                val biometricPrompt = BiometricPrompt(ContextCompat.getMainExecutor(context), callback)
                val promptInfo = createBiometricPromptInfo(context)
                biometricPrompt.authenticate(promptInfo)
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE,
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE,
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                // handle error cases
            }
        }
    }*/
}