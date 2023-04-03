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

    private val nftBitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
    private val bodyList = arrayListOf(R.drawable.ic_kakao_login, R.drawable.ic_home_nft)
    private val headList = arrayListOf(R.drawable.ic_kakao_login, R.drawable.ic_home_nft)
    private val chipList = arrayListOf(R.drawable.ic_kakao_login, R.drawable.ic_home_nft)
    private val eyeList = arrayListOf(R.drawable.ic_kakao_login, R.drawable.ic_home_nft)
    private val mouthList = arrayListOf(R.drawable.ic_kakao_login, R.drawable.ic_home_nft)
    private val earphonesList = arrayListOf(R.drawable.ic_kakao_login, R.drawable.ic_home_nft)

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
            }
        }
    }

    private fun makeNFT(songItem: SongItem, background: Bitmap?) {

        canvas = Canvas(nftBitmap)
        val drawable0: Drawable = BitmapDrawable(background)
        drawable0.setBounds(100, 100, 400, 400)
        drawable0.draw(canvas)

//        drawBitmap(bodyList, songItem.combination!![0])
//        drawBitmap(headList, songItem.combination[1])
//        drawBitmap(chipList, songItem.combination[2])
//        drawBitmap(eyeList, songItem.combination[3])
//        drawBitmap(mouthList, songItem.combination[4])
//        drawBitmap(earphonesList, songItem.combination[5])
    }

    override fun onSuccess(bitmap: Bitmap?) {
        makeNFT(songItem, bitmap)
    }

    override fun onError(e: Exception) {

    }

    private fun drawBitmap(list: List<Int>, loc: Int) {
        val res: Resources = activity.resources
        val bitmap1 = BitmapFactory.decodeResource(res, list[loc])
        val drawable1: Drawable = BitmapDrawable(bitmap1)
        drawable1.setBounds(100, 100, 400, 400)
        drawable1.draw(canvas)
    }
}