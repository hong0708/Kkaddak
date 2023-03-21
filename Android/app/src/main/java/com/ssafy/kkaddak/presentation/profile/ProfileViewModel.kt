package com.ssafy.kkaddak.presentation.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.profile.ProfileItem
import com.ssafy.kkaddak.domain.usecase.profile.GetProfileInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileInfoUseCase: GetProfileInfoUseCase
) : ViewModel() {

    private val _profileData: MutableLiveData<ProfileItem?> = MutableLiveData()
    val profileData: LiveData<ProfileItem?> = _profileData

    fun getProfileInfo(nickname: String) = viewModelScope.launch {
        when (val value = getProfileInfoUseCase(nickname)) {
            is Resource.Success<ProfileItem> -> {
                _profileData.value = value.data
            }
            is Resource.Error -> {
                Log.e("getProfileInfo", "getProfileInfo: ${value.errorMessage}")
            }
        }
    }
}