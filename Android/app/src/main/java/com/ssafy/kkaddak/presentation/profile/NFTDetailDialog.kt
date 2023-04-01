package com.ssafy.kkaddak.presentation.profile

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.DialogNftDetailBinding
import com.ssafy.kkaddak.domain.entity.profile.ProfileNFTDetailItem
import java.io.File
import java.io.FileOutputStream

class NFTDetailDialog(
    val activity: Activity,
    private val nftDetail: ProfileNFTDetailItem,
    private val isMine: Boolean
) : Dialog(activity) {

    private lateinit var binding: DialogNftDetailBinding
    private lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogNftDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        initListener()
        binding.apply {
            if (!isMine) {
                ivNftHomeProfile.visibility = View.GONE
                ivImageDownload.visibility = View.GONE
                ivImageShare.visibility = View.GONE
            }
            nftDetail = this@NFTDetailDialog.nftDetail
        }
    }

    private fun initListener() {
        binding.apply {
            ivImageDownload.setOnClickListener {
                binding.apply {
                    ivNft.isDrawingCacheEnabled = true
                    ivNft.buildDrawingCache()
                    bitmap = Bitmap.createBitmap(ivNft.drawingCache)
                    ivNft.isDrawingCacheEnabled = false
                }
                saveImageToGallery()
            }
            ivCloseNftInfoDialog.setOnClickListener { dismiss() }
        }
    }

    private fun saveImageToGallery() {
        // 권한 체크
        if (!checkPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) ||
            !checkPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ) {
            requestPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            return
        }

        // 그림 저장
        if (!imageExternalSave(activity, bitmap, activity.getString(R.string.app_name))) {
            Toast.makeText(activity, "그림 저장을 실패하였습니다", Toast.LENGTH_SHORT).show()
        }
        Toast.makeText(activity, "그림이 갤러리에 저장되었습니다", Toast.LENGTH_SHORT).show()
    }

    private fun imageExternalSave(context: Context, bitmap: Bitmap, path: String): Boolean {

        val state = Environment.getExternalStorageState()

        if (Environment.MEDIA_MOUNTED == state) {
            val rootPath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .toString()
            val dirName = "/" + path
            val fileName = System.currentTimeMillis().toString() + ".png"
            val savePath = File(rootPath + dirName)
            savePath.mkdirs()

            val file = File(savePath, fileName)
            if (file.exists()) file.delete()

            try {
                val out = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                out.flush()
                out.close()

                // 갤러리 갱신
                context.sendBroadcast(
                    Intent(
                        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                        Uri.parse("file://" + Environment.getExternalStorageDirectory())
                    )
                )
                return true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return false
    }

    private fun checkPermission(activity: Activity, permission: String): Boolean {
        val permissionChecker =
            ContextCompat.checkSelfPermission(activity.applicationContext, permission)
        // 권한이 없으면 권한 요청
        if (permissionChecker == PackageManager.PERMISSION_GRANTED) return true
        requestPermission(activity, permission)
        return false
    }

    private fun requestPermission(activity: Activity, permission: String) {
        ActivityCompat.requestPermissions(activity, arrayOf(permission), 1)
    }
}