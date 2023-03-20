package com.ssafy.kkaddak.presentation.join

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kkaddak.ApplicationClass
import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.data.remote.datasource.auth.AuthRequest
import com.ssafy.kkaddak.domain.entity.auth.Token
import com.ssafy.kkaddak.domain.entity.user.User
import com.ssafy.kkaddak.domain.usecase.auth.GetNewTokenUseCase
import com.ssafy.kkaddak.domain.usecase.auth.LoginUseCase
import com.ssafy.kkaddak.domain.usecase.user.CheckDuplicationUseCase
import com.ssafy.kkaddak.domain.usecase.user.CreateUserInfoUseCase
import com.ssafy.kkaddak.domain.usecase.user.RequestCancelSignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class JoinViewModel @Inject constructor(
    private val createUserInfoUseCase: CreateUserInfoUseCase,
    private val loginUseCase: LoginUseCase,
    private val getNewTokenUseCase: GetNewTokenUseCase,
    private val checkDuplicationUseCase: CheckDuplicationUseCase,
    private val requestCancelSignUpUseCase: RequestCancelSignUpUseCase
) : ViewModel() {

    private val _accessToken = MutableLiveData<String?>()

    private val _refreshToken = MutableLiveData<String?>()
    val refreshToken = _refreshToken

    private val _isExist = MutableLiveData<Boolean?>()
    val isExist = _isExist

    private val _isDuplicate: MutableLiveData<Boolean?> = MutableLiveData(null)
    val isDuplicate: MutableLiveData<Boolean?> = _isDuplicate

    private val _isSuccess: MutableLiveData<Boolean?> = MutableLiveData(false)
    val isSuccess: MutableLiveData<Boolean?> = _isSuccess

    private val _profileImgUri: MutableLiveData<Uri?> = MutableLiveData(null)
    val profileImgUri: MutableLiveData<Uri?> = _profileImgUri

    private var profileImgMultiPart: MultipartBody.Part? = null

    private val _nickname: MutableLiveData<String> = MutableLiveData(null)
    val nickname: MutableLiveData<String> = _nickname

    private var state = false

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

    fun setProfileImg(uri: Uri, file: File) {
        viewModelScope.launch {
            _profileImgUri.value = uri
            val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            profileImgMultiPart =
                MultipartBody.Part.createFormData("profileImg", file.name, requestFile)
        }
    }

    fun updateProfile() {
        viewModelScope.launch {
            when (val value =
                createUserInfoUseCase.createUserInfo(
                    requireNotNull(_nickname.value),
                    profileImgMultiPart
                )) {
                is Resource.Success<User> -> {
                    ApplicationClass.preferences.nickname = _nickname.value
                    ApplicationClass.preferences.isLoggedIn = true
                    _isSuccess.value = true
                }
                is Resource.Error -> {
                    Log.e("updateprofile", "updateProfile: ${value.errorMessage}")

                }
            }
        }
    }

    fun updateProfileWithoutImg() {
        viewModelScope.launch {
            when (val value =
                createUserInfoUseCase.createUserInfoWithoutImg(
                    requireNotNull(_nickname.value)
                )) {
                is Resource.Success<User> -> {
                    ApplicationClass.preferences.nickname = _nickname.value
                    ApplicationClass.preferences.isLoggedIn = true
                    _isSuccess.value = true
                }
                is Resource.Error -> {
                    Log.e("updateprofile", "updateProfile: ${value.errorMessage}")

                }
            }
        }
    }

    suspend fun checkDuplication() =
        viewModelScope.async {
            when (val value = checkDuplicationUseCase.checkDuplication(nickname.value!!)) {
                is Resource.Success<Boolean> -> {
                    _isDuplicate.value = value.data
                    return@async 1
                }
                is Resource.Error -> {
                    Log.e("checkDuplication", "checkDuplication: ${value.errorMessage}")
                    return@async 0
                }
            }
        }.await()

    fun returnDuplicationTrue() {
        _isDuplicate.value = true
    }

    fun cancelSignUp(): Boolean {
        viewModelScope.launch {
            when (val value = requestCancelSignUpUseCase()) {
                is Resource.Success<Boolean> -> {
                    state = value.data
                }
                is Resource.Error -> {
                    Log.e("cancelSignUp", "cancelSignUp: ${value.errorMessage}")
                }
            }
        }
        return state
    }
}