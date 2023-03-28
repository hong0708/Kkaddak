package com.ssafy.kkaddak.presentation.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kkaddak.ApplicationClass
import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.profile.ProfileItem
import com.ssafy.kkaddak.domain.entity.song.SongItem
import com.ssafy.kkaddak.domain.usecase.profile.DeleteMySongUseCase
import com.ssafy.kkaddak.domain.usecase.profile.GetProfileInfoUseCase
import com.ssafy.kkaddak.domain.usecase.profile.GetProfileSongUseCase
import com.ssafy.kkaddak.domain.usecase.profile.RequestFollowUseCase
import com.ssafy.kkaddak.domain.usecase.user.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val getProfileSongUseCase: GetProfileSongUseCase,
    private val deleteMySongUseCase: DeleteMySongUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val requestFollowUseCase: RequestFollowUseCase
) : ViewModel() {

    private val _profileData: MutableLiveData<ProfileItem?> = MutableLiveData()
    val profileData: LiveData<ProfileItem?> = _profileData

    private val _profileSongData: MutableLiveData<List<SongItem>?> = MutableLiveData()
    val profileSongData: LiveData<List<SongItem>?> = _profileSongData

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

    fun getProfileSong(nickname: String) = viewModelScope.launch {
        when (val value = getProfileSongUseCase(nickname)) {
            is Resource.Success<List<SongItem>> -> {
                _profileSongData.value = value.data
            }
            is Resource.Error -> {
                Log.e("getProfileSong", "getProfileSong: ${value.errorMessage}")
            }
        }
    }

    fun deleteMySong(songId: String) = viewModelScope.launch {
        deleteMySongUseCase(songId)
    }

    fun requestLogout() = viewModelScope.launch {
        logoutUseCase(
            ApplicationClass.preferences.accessToken!!,
            ApplicationClass.preferences.refreshToken!!
        )
    }

    fun followArtist(artistId: String) = viewModelScope.launch {
        requestFollowUseCase.followArtist(artistId)
    }

    fun unfollowArtist(artistId: String) = viewModelScope.launch {
        requestFollowUseCase.unfollowArtist(artistId)
    }
}