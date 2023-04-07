package com.ssafy.kkaddak.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.home.HomeProfile
import com.ssafy.kkaddak.domain.entity.song.SongItem
import com.ssafy.kkaddak.domain.usecase.home.GetHomeProfileUseCase
import com.ssafy.kkaddak.domain.usecase.home.GetLatestSongsUseCase
import com.ssafy.kkaddak.domain.usecase.home.GetPopularSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLatestSongsUseCase: GetLatestSongsUseCase,
    private val getHomeProfileUseCase: GetHomeProfileUseCase,
    private val getPopularSongsUseCase: GetPopularSongsUseCase
) : ViewModel() {

    private val _latestSongsList: MutableLiveData<List<SongItem>?> = MutableLiveData()
    val latestSongsList: LiveData<List<SongItem>?> = _latestSongsList

    private val _popularSongsList: MutableLiveData<List<SongItem>?> = MutableLiveData()
    val popularSongsList: LiveData<List<SongItem>?> = _popularSongsList

    private val _homeProfile: MutableLiveData<HomeProfile?> = MutableLiveData()
    val homeProfile: LiveData<HomeProfile?> = _homeProfile

    fun getLatestSongs() = viewModelScope.launch {
        when (val value = getLatestSongsUseCase()) {
            is Resource.Success<List<SongItem>> -> {
                _latestSongsList.value = value.data
            }
            is Resource.Error -> {
                Log.e("getLatestSongs", "getLatestSongs: ${value.errorMessage}")
            }
        }
    }

    fun getPopularSongs() = viewModelScope.launch {
        when (val value = getPopularSongsUseCase()) {
            is Resource.Success<List<SongItem>> -> {
                _popularSongsList.value = value.data
            }
            is Resource.Error -> {
                Log.e("getPopularSongs", "getPopularSongs: ${value.errorMessage}")
            }
        }
    }

    fun getHomeProfile() = viewModelScope.launch {
        when (val value = getHomeProfileUseCase()) {
            is Resource.Success<HomeProfile> -> {
                _homeProfile.value = value.data
            }
            is Resource.Error -> {
                Log.e("getHomeProfile", "getHomeProfile: ${value.errorMessage}")
            }
        }
    }
}