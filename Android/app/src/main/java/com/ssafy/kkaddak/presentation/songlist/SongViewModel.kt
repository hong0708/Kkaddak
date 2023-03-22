package com.ssafy.kkaddak.presentation.songlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.song.SongItem
import com.ssafy.kkaddak.domain.usecase.song.CancelBookmarkUseCase
import com.ssafy.kkaddak.domain.usecase.song.GetPlayListUseCase
import com.ssafy.kkaddak.domain.usecase.song.GetSongDetailUseCase
import com.ssafy.kkaddak.domain.usecase.song.GetSongsUseCase
import com.ssafy.kkaddak.domain.usecase.song.RequestBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(
    private val getSongsUseCase: GetSongsUseCase,
    private val requestBookmarkUseCase: RequestBookmarkUseCase,
    private val cancelBookmarkUseCase: CancelBookmarkUseCase,
    private val getSongDetailUseCase: GetSongDetailUseCase,
    private val getPlayListUseCase: GetPlayListUseCase
) : ViewModel() {

    private val _songListData: MutableLiveData<List<SongItem>?> = MutableLiveData()
    val songListData: LiveData<List<SongItem>?> = _songListData

    private val _songData: MutableLiveData<SongItem?> = MutableLiveData()
    val songData: LiveData<SongItem?> = _songData

    private val _playListData: MutableLiveData<List<SongItem>?> = MutableLiveData()
    val playListData: LiveData<List<SongItem>?> = _playListData

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

    fun requestBookmark(songId: String) = viewModelScope.launch {
        requestBookmarkUseCase(songId)
    }

    fun cancelBookmark(songId: String) = viewModelScope.launch {
        cancelBookmarkUseCase(songId)
    }

    fun getSong(songId: String) = viewModelScope.launch {
        when (val value = getSongDetailUseCase(songId)) {
            is Resource.Success<SongItem> -> {
                _songData.value = value.data
            }
            is Resource.Error -> {
                Log.e("getSong", "getSong: ${value.errorMessage}")
            }
        }
    }

    fun getPlayList() = viewModelScope.launch {
        when (val value = getPlayListUseCase()) {
            is Resource.Success<List<SongItem>> -> {
                _playListData.value = value.data
            }
            is Resource.Error -> {
                Log.e("getPlayList", "getPlayList: ${value.errorMessage}")
            }
        }
    }
}
