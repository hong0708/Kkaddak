package com.ssafy.kkaddak.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.song.SongItem
import com.ssafy.kkaddak.domain.usecase.home.GetLatestSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLatestSongsUseCase: GetLatestSongsUseCase
) : ViewModel() {

    private val _latestSongsList: MutableLiveData<List<SongItem>?> = MutableLiveData()
    val latestSongsList: LiveData<List<SongItem>?> = _latestSongsList

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
}