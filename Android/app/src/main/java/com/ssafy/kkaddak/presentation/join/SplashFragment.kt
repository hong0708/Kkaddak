package com.ssafy.kkaddak.presentation.join

import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.common.util.fadeInView
import com.ssafy.kkaddak.common.util.fadeOutView
import com.ssafy.kkaddak.data.local.datasource.SharedPreferences
import com.ssafy.kkaddak.data.remote.datasource.auth.AuthRequest
import com.ssafy.kkaddak.databinding.FragmentSplashBinding
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(R.layout.fragment_splash) {

    private val joinViewModel by viewModels<JoinViewModel>()

    override fun initView() {}

    override fun onResume() {
        super.onResume()

        CoroutineScope(Dispatchers.Main).launch {
            // 프로젝트 진행을 위해 스킵
            fadeInView(binding.ivSplashMain, requireContext())
            fadeInView(binding.test, requireContext())

            delay(1000)

            val fadeInAnim = AnimationUtils.loadAnimation(context, R.anim.jump).apply {
                //interpolator = AccelerateInterpolator()

                interpolator = AccelerateInterpolator()
                setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {}

                    override fun onAnimationEnd(animation: Animation) {
                        val reverseAnimation =
                            AnimationUtils.loadAnimation(context, R.anim.jump_reverse)
                                .apply {
                                    interpolator = OvershootInterpolator()
                                }
                        view?.startAnimation(reverseAnimation)
                    }

                    override fun onAnimationRepeat(animation: Animation) {}
                })
            }
            binding.test.startAnimation(fadeInAnim)

            Glide.with(requireActivity())
                .load(R.raw.splash)
                .into(binding.ivSplashMain)

            delay(3000)
            fadeOutView(binding.ivSplashMain, requireContext())
            fadeOutView(binding.clSplash, requireContext())
            delay(1000)

            if (SharedPreferences(requireContext()).isLoggedIn) {
                joinViewModel.requestLogin(AuthRequest(SharedPreferences(requireContext()).accessToken!!))
                navigate(SplashFragmentDirections.actionSplashFragmentToMainActivity())
            } else {
                navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
            }
        }
    }
}