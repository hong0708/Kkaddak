package com.ssafy.kkaddak.presentation.join

import android.content.Intent
import android.view.View
import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.FragmentSplashBinding
import com.ssafy.kkaddak.presentation.MainActivity
import com.ssafy.kkaddak.presentation.base.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment: BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {
    override fun initView() {
        CoroutineScope(Dispatchers.Main).launch {
            fadeInView(binding.ivSplashMain)
            delay(1000)
            Glide.with(requireActivity())
                .load(R.raw.splash)
                .into(binding.ivSplashMain)
            delay(3000)
            fadeOutView(binding.ivSplashMain)
            delay(1000)

            navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
        }
    }
    // fade in, fade out
    private fun fadeInView(view: View) {
        val fadeInAnim = AnimationUtils.loadAnimation(context, R.anim.fadein)
        view.startAnimation(fadeInAnim)
        view.visibility = View.VISIBLE
    }

    private fun fadeOutView(view: View) {
        val fadeOutAnim = AnimationUtils.loadAnimation(context, R.anim.fadeout)
        view.startAnimation(fadeOutAnim)
        view.visibility = View.GONE
    }
}