package com.ssafy.kkaddak.presentation.songlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.song.SongItem
import com.ssafy.kkaddak.domain.usecase.song.CancelBookmarkUseCase
import com.ssafy.kkaddak.domain.usecase.song.GetSongsUseCase
import com.ssafy.kkaddak.domain.usecase.song.RequestBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(
    private val getSongsUseCase: GetSongsUseCase,
    private val requestBookmarkUseCase: RequestBookmarkUseCase,
    private val cancelBookmarkUseCase: CancelBookmarkUseCase
) : ViewModel() {

    private val _songListData: MutableLiveData<List<SongItem>?> = MutableLiveData()
    val songListData: LiveData<List<SongItem>?> = _songListData

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
}