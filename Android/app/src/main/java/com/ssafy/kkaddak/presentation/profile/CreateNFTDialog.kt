package com.ssafy.kkaddak.presentation.profile

import android.annotation.SuppressLint
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
import com.ssafy.kkaddak.common.util.UrlToBitmap
import com.ssafy.kkaddak.databinding.DialogCreateNftBinding
import com.ssafy.kkaddak.domain.entity.song.SongItem

class CreateNFTDialog(
    val activity: Activity,
    private val songItem: SongItem,
    private val listener: CreateNFTDialogInterface
) : Dialog(activity), UrlToBitmap.UrlToBitmapListener {

    private lateinit var binding: DialogCreateNftBinding
    private lateinit var canvas: Canvas
    private val nftBitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888)

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

        val task = UrlToBitmap(this)
        task.execute(songItem.coverPath)

        initListener()
    }

    private fun initListener() {
        binding.apply {
            tvCancelCreateNft.setOnClickListener { dismiss() }

            tvConfirmCreateNft.setOnClickListener {
                listener.mintNFT(songItem, nftBitmap)
                dismiss()
            }
        }
    }

    @SuppressLint("DiscouragedApi")
    private fun makeNFT(songItem: SongItem, background: Bitmap?) {

        canvas = Canvas(nftBitmap)
        val drawable0: Drawable = BitmapDrawable(background)
        drawable0.setBounds(0, 0, 1000, 1000)
        drawable0.draw(canvas)

        drawBitmap(
            activity.resources.getIdentifier(
                "body${songItem.combination!![0]}",
                "drawable",
                activity.packageName
            )
        )
        drawBitmap(
            activity.resources.getIdentifier(
                "head${songItem.combination[1]}",
                "drawable",
                activity.packageName
            )
        )
        drawBitmap(
            activity.resources.getIdentifier(
                "check${songItem.combination[2]}",
                "drawable",
                activity.packageName
            )
        )
        drawBitmap(
            activity.resources.getIdentifier(
                "eyes${songItem.combination[3]}",
                "drawable",
                activity.packageName
            )
        )
        drawBitmap(
            activity.resources.getIdentifier(
                "mouth${songItem.combination[4]}",
                "drawable",
                activity.packageName
            )
        )
        drawBitmap(
            activity.resources.getIdentifier(
                "acc_${songItem.combination[5]}",
                "drawable",
                activity.packageName
            )
        )
    }

    override fun onSuccess(bitmap: Bitmap?) {
        makeNFT(songItem, bitmap)
    }

    override fun onError(e: Exception) {

    }

    private fun drawBitmap(drawable: Int) {
        val res: Resources = activity.resources
        val bitmap1 = BitmapFactory.decodeResource(res, drawable)
        val drawable1: Drawable = BitmapDrawable(bitmap1)
        drawable1.setBounds(0, 0, 1000, 1000)
        drawable1.draw(canvas)
    }
}