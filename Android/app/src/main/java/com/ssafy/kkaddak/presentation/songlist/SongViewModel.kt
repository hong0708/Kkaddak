package com.ssafy.kkaddak.presentation.songlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.song.SongItem
import com.ssafy.kkaddak.domain.usecase.song.*
import com.ssafy.kkaddak.domain.usecase.song.GetPlayListUseCase
import com.ssafy.kkaddak.domain.usecase.song.GetSongDetailUseCase
import com.ssafy.kkaddak.domain.usecase.song.GetSongsUseCase
import com.ssafy.kkaddak.domain.usecase.song.RequestBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(
    private val getSongsUseCase: GetSongsUseCase,
    private val requestBookmarkUseCase: RequestBookmarkUseCase,
    private val getSongDetailUseCase: GetSongDetailUseCase,
    private val getPlayListUseCase: GetPlayListUseCase,
    private val searchMusicUseCase: SearchMusicUseCase
) : ViewModel() {

    private val _songListData: MutableLiveData<List<SongItem>?> = MutableLiveData()
    val songListData: LiveData<List<SongItem>?> = _songListData

    private val _songData: MutableLiveData<SongItem?> = MutableLiveData()
    val songData: LiveData<SongItem?> = _songData

    private val _playListData: MutableLiveData<List<SongItem>?> = MutableLiveData()
    val playListData: LiveData<List<SongItem>?> = _playListData

    private val _searchedListData: MutableLiveData<List<SongItem>?> = MutableLiveData()
    val searchedListData: LiveData<List<SongItem>?> = _searchedListData

    var keyword = ""
    var filter: Array<String> = arrayOf()

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

    suspend fun requestBookmark(songId: String) = viewModelScope.async {
        when (val value = requestBookmarkUseCase(songId)) {
            is Resource.Success<Boolean> -> return@async value.data.toString()
            is Resource.Error -> {
                Log.e("requestBookmark", "requestBookmark: ${value.errorMessage}")
                return@async "error"
            }
        }
    }.await()

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

    fun searchMusic(keyWord: String, filter: String) = viewModelScope.launch {
        when (val value = searchMusicUseCase(keyWord, filter)) {
            is Resource.Success<List<SongItem>> -> {
                _songListData.value = value.data
            }
            is Resource.Error -> {
                Log.e("searchMusic", "searchMusic: ${value.errorMessage}")
            }
        }
    }
}
