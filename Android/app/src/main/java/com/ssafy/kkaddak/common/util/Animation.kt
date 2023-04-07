package com.ssafy.kkaddak.common.util

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import android.view.animation.TranslateAnimation
import com.ssafy.kkaddak.R

// fade in, fade out
fun fadeInView(view: View, context: Context) {
    val fadeInAnim = AnimationUtils.loadAnimation(context, R.anim.fadein)
    view.startAnimation(fadeInAnim)
    view.visibility = View.VISIBLE
}

fun fadeOutView(view: View, context: Context) {
    val fadeOutAnim = AnimationUtils.loadAnimation(context, R.anim.fadeout)
    view.startAnimation(fadeOutAnim)
    view.visibility = View.GONE
}

// logo jump
fun jumpView(view: View) {
    val animation =
        TranslateAnimation(0f, 0f, 0f, -100f)
    animation.duration = 500
    animation.interpolator = OvershootInterpolator()
    animation.repeatMode = Animation.REVERSE
    animation.repeatCount = 1

    view.startAnimation(animation)
}