package com.ssafy.kkaddak.presentation.join

import android.util.Log
import androidx.fragment.app.viewModels
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.common.util.fadeInView
import com.ssafy.kkaddak.data.remote.datasource.auth.AuthRequest
import com.ssafy.kkaddak.databinding.FragmentLoginBinding
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val joinViewModel by viewModels<JoinViewModel>()
    private val callback = initKakaoLoginCallback()

    override fun initView() {
        initListener()
        observeJoinViewModel()
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.Main).launch {
            fadeInView(binding.clLogin, requireContext())
            delay(1000)
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
            navigate(LoginFragmentDirections.actionLoginFragmentToJoinFragment())
        } else if (token != null) {
            Log.i("login", "카카오계정으로 로그인 성공 ${token.accessToken}")
            joinViewModel.requestLogin(AuthRequest(token.accessToken))
            if (joinViewModel.refreshToken.value == "") {
                joinViewModel.requestLogin(AuthRequest(token.accessToken))
            }
        }
    }

    private fun observeJoinViewModel() {
        joinViewModel.isExist.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    navigate(LoginFragmentDirections.actionLoginFragmentToMainActivity())
                }
                false -> {
                    navigate(LoginFragmentDirections.actionLoginFragmentToJoinFragment())
                }
                else -> {}
            }
        }
    }
}