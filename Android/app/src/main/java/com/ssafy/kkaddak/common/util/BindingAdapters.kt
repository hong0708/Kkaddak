package com.ssafy.kkaddak.common.util

import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ssafy.kkaddak.R
import java.math.BigInteger
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("android:profileImgUri")
    fun ImageView.setProfileImg(imgUri: Uri?) {
        Glide.with(this.context)
            .load(imgUri)
            .placeholder(R.drawable.ic_profile_image)
            .error(R.drawable.ic_profile_image)
            .circleCrop()
            .into(this)
    }

    @JvmStatic
    @BindingAdapter("android:normalImgUri")
    fun ImageView.setNormalImg(imgUri: Uri?) {
        Glide.with(this.context)
            .load(imgUri)
            .into(this)
    }

    @JvmStatic
    @BindingAdapter("android:profileImgStr")
    fun ImageView.setProfileImg(imgUri: String?) {
        Glide.with(this.context)
            .load("http://j8d208.p.ssafy.io:8087/images$imgUri")
            .placeholder(R.drawable.ic_profile_image)
            .error(R.drawable.ic_profile_image)
            .circleCrop()
            .into(this)
    }

    @JvmStatic
    @BindingAdapter("android:normalImgStr")
    fun ImageView.setNormalImg(imgUri: String?) {
        Glide.with(this.context)
            .load("http://j8d208.p.ssafy.io:8087/images$imgUri")
            .placeholder(R.drawable.bg_image_not_found)
            .error(R.drawable.bg_image_not_found)
            .into(this)
    }

    @JvmStatic
    @BindingAdapter("android:coverNFTImgStr")
    fun ImageView.setCoverNFTImg(imgUri: String?) {
        Glide.with(this.context)
            .load(imgUri)
            .placeholder(R.drawable.bg_image_not_found)
            .error(R.drawable.bg_image_not_found)
            .into(this)
    }

    @JvmStatic
    @BindingAdapter("android:setDateFormat")
    fun TextView.setDateFormat(date: BigInteger?) {
        val format = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        this.text = format.format(Date(date!!.toLong() * 1000)).toString()
    }
}