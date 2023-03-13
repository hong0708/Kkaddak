package com.ssafy.kkaddak.presentation.join

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kkaddak.ApplicationClass
import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.data.remote.datasource.auth.AuthRequest
import com.ssafy.kkaddak.domain.entity.auth.Token
import com.ssafy.kkaddak.domain.usecase.auth.GetNewTokenUseCase
import com.ssafy.kkaddak.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JoinViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getNewTokenUseCase: GetNewTokenUseCase
) : ViewModel() {

    private val _accessToken = MutableLiveData<String?>()

    private val _refreshToken = MutableLiveData<String?>()
    val refreshToken = _refreshToken

    private val _isExist = MutableLiveData<Boolean?>()
    val isExist = _isExist

    fun requestLogin(body: AuthRequest) = viewModelScope.launch {
        when (val value = loginUseCase(body)) {
            is Resource.Success<Token> -> {
                val accessToken = value.data.accessToken
                val refreshToken = value.data.refreshToken

                ApplicationClass.preferences.accessToken = accessToken
                ApplicationClass.preferences.refreshToken = refreshToken

                _accessToken.value = accessToken
                _refreshToken.value = refreshToken
                _isExist.value = value.data.isExist
                ApplicationClass.preferences.isLoggedIn = true
            }
            is Resource.Error -> {
                Log.d("requestLogin", "requestLogin: ${value.errorMessage}")
                if (value.errorMessage == "401") {
                    getNewToken()
                }
            }
        }
    }

    private fun getNewToken() = viewModelScope.launch {
        when (val value = getNewTokenUseCase()) {
            is Resource.Success -> {
                val accessToken = value.data.accessToken
                ApplicationClass.preferences.accessToken = accessToken
                _accessToken.value = accessToken
                _refreshToken.value = ""
            }
            is Resource.Error -> {
                Log.d("getNewToken", "getNewToken: ${value.errorMessage}")
            }
        }
    }
}