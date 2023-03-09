package com.ssafy.kkaddak.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.ActivitySplashBinding
import com.ssafy.kkaddak.presentation.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.Main).launch {
            fadeInView(binding.ivSplashMain)
            delay(1000)
            Glide.with(this@SplashActivity)
                .load(R.raw.splash)
                .into(binding.ivSplashMain)
            delay(3000)
            fadeOutView(binding.ivSplashMain)
            delay(1000)

            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    // fade in, fade out
    private fun fadeInView(view: View) {
        val fadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fadein)
        view.startAnimation(fadeInAnim)
        view.visibility = View.VISIBLE
    }

    private fun fadeOutView(view: View) {
        val fadeOutAnim = AnimationUtils.loadAnimation(this, R.anim.fadeout)
        view.startAnimation(fadeOutAnim)
        view.visibility = View.GONE
    }
}