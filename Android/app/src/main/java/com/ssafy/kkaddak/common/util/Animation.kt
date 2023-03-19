package com.ssafy.kkaddak.common.util

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
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