package com.ssafy.kkaddak.common.util

import android.annotation.SuppressLint
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.os.Build
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


@SuppressLint("PackageManagerGetSignatures")
fun getHashKey(activity: AppCompatActivity) {
    lateinit var packageInfo: PackageInfo
    lateinit var signatures: Array<Signature>

    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo = activity.packageManager.getPackageInfo(
                activity.packageName, PackageManager.GET_SIGNING_CERTIFICATES
            )
            signatures = packageInfo.signingInfo.apkContentsSigners
        } else {
            packageInfo = activity.packageManager.getPackageInfo(
                activity.packageName, PackageManager.GET_SIGNATURES
            )
            signatures = packageInfo.signatures
        }
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }

    for (signature in signatures) {
        try {
            val md: MessageDigest = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
            Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
        } catch (e: NoSuchAlgorithmException) {
            Log.e("KeyHash", "Unable to get MessageDigest. signature=$signature", e)
        }
    }
}