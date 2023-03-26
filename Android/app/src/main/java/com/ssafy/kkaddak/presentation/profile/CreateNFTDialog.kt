package com.ssafy.kkaddak.presentation.profile

import android.app.Activity
import android.app.Dialog
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.WindowManager
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.DialogCreateNftBinding

class CreateNFTDialog(
    val activity: Activity,
) : Dialog(activity) {

    private lateinit var binding: DialogCreateNftBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogCreateNftBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        initListener()
    }
    private fun initListener() {
        binding.apply{
            tvCancelCreateNft.setOnClickListener { dismiss() }
            tvConfirmCreateNft.setOnClickListener {
                makeNFT()
            }
        }
    }

    private fun makeNFT(){
        val bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
        val c = Canvas(bitmap)
        val res: Resources = activity.resources

        val bitmap1 = BitmapFactory.decodeResource(res, R.drawable.ic_camera)
        val drawable1: Drawable = BitmapDrawable(bitmap1)
        drawable1.setBounds(100, 100, 400, 400)
        drawable1.draw(c)

        // 완성한 비트맵 nft 트랜잭션 발생
    }
}