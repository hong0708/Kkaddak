package com.ssafy.kkaddak.presentation.songlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.song.SongItem
import com.ssafy.kkaddak.domain.usecase.song.GetSongDetailUseCase
import com.ssafy.kkaddak.domain.usecase.song.GetSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(
    private val getSongsUseCase: GetSongsUseCase,
    private val getSongDetailUseCase: GetSongDetailUseCase
) : ViewModel() {

    private val _songListData: MutableLiveData<List<SongItem>?> = MutableLiveData()
    val songListData: LiveData<List<SongItem>?> = _songListData

    private val _songData: MutableLiveData<SongItem?> = MutableLiveData()
    val songData: LiveData<SongItem?> = _songData

    fun getSongs() = viewModelScope.launch {
        when (val value = getSongsUseCase()) {
            is Resource.Success<List<SongItem>> -> {
                _songListData.value = value.data
            }
            is Resource.Error -> {
                Log.e("getSongs", "getSongs: ${value.errorMessage}")
            }
        }
    }

    fun getSong(songId: Int) = viewModelScope.launch {
        when (val value = getSongDetailUseCase(songId)) {
            is Resource.Success<SongItem> -> {
                _songData.value = value.data
            }
            is Resource.Error -> {
                Log.e("getSong", "getSong: ${value.errorMessage}")
            }
        }
    }
}