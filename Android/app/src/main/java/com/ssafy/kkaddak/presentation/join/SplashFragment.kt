package com.ssafy.kkaddak.presentation.join

import android.util.Log
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.common.util.fadeInView
import com.ssafy.kkaddak.common.util.jumpView
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
    private val callback = initKakaoLoginCallback()

    override fun initView() {
        initListener()
        isMember()
    }

    override fun onResume() {
        super.onResume()

        CoroutineScope(Dispatchers.Main).launch {
            binding.apply {
                delay(1500)

                jumpView(ivLogo1)
                delay(250)
                ivLogo1.setImageResource(R.drawable.ic_word_cinnabar1)

                jumpView(ivLogo2)
                delay(250)
                ivLogo2.setImageResource(R.drawable.ic_word_cinnabar2)

                jumpView(ivLogo3)
                delay(250)
                ivLogo3.setImageResource(R.drawable.ic_word_cinnabar1)

                jumpView(ivLogo4)
                ivLogo4.setImageResource(R.drawable.ic_word_cinnabar2)
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            // 프로젝트 진행을 위해 스킵
            fadeInView(binding.clSplash, requireContext())
            Glide.with(requireActivity())
                .load(R.raw.splash)
                .into(binding.ivSplashMain)

            delay(4000)

            if (SharedPreferences(requireContext()).isLoggedIn) {
                // 자동 로그인 시 유효성 검사 추가 필요
                // joinViewModel.requestLogin(AuthRequest(SharedPreferences(requireContext()).accessToken!!))
                navigate(SplashFragmentDirections.actionSplashFragmentToMainActivity())
            } else {
                fadeInView(binding.clKakaoLogin, requireContext())
            }
        }
    }

    private fun initListener() {
        binding.clKakaoLogin.setOnClickListener {
            kakaoLogin()
        }
    }

    private fun kakaoLogin() {
        UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
    }

    private fun initKakaoLoginCallback(): (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("login", "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i("login", "카카오계정으로 로그인 성공 ${token.accessToken}")
            joinViewModel.requestLogin(AuthRequest(token.accessToken))
            if (joinViewModel.refreshToken.value == "") {
                joinViewModel.requestLogin(AuthRequest(token.accessToken))
            }
        }
    }

    private fun isMember() {
        joinViewModel.isExist.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    navigate(SplashFragmentDirections.actionSplashFragmentToMainActivity())
                }
                false -> {
                    navigate(SplashFragmentDirections.actionSplashFragmentToJoinFragment())
                }
                else -> {}
            }
        }
    }
}