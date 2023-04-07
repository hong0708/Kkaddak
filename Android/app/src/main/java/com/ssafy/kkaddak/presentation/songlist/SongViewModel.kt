package com.ssafy.kkaddak.presentation.songlist

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.song.SongItem
import com.ssafy.kkaddak.domain.usecase.song.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(
    private val getSongsUseCase: GetSongsUseCase,
    private val requestBookmarkUseCase: RequestBookmarkUseCase,
    private val getSongDetailUseCase: GetSongDetailUseCase,
    private val getPlayListUseCase: GetPlayListUseCase,
    private val getLikeListUseCase: GetLikeListUseCase,
    private val searchMusicUseCase: SearchMusicUseCase,
    private val deletePlayListUseCase: DeletePlayListUseCase,
    private val uploadSongUseCase: UploadSongUseCase
) : ViewModel() {

    private val _songListData: MutableLiveData<List<SongItem>?> = MutableLiveData()
    val songListData: LiveData<List<SongItem>?> = _songListData

    private val _songData: MutableLiveData<SongItem?> = MutableLiveData()
    val songData: MutableLiveData<SongItem?> = _songData

    private val _playListData: MutableLiveData<List<SongItem>?> = MutableLiveData()
    val playListData: LiveData<List<SongItem>?> = _playListData

    private val _likeListData: MutableLiveData<List<SongItem>?> = MutableLiveData()
    val likeListData: LiveData<List<SongItem>?> = _likeListData

    // Create
    private val _coverFile: MutableLiveData<Uri?> = MutableLiveData()
    val coverFile: MutableLiveData<Uri?> = _coverFile

    private var coverFileMultiPart: MultipartBody.Part? = null

    private val _songFile: MutableLiveData<Uri?> = MutableLiveData()
    val songFile: MutableLiveData<Uri?> = _songFile

    private var songFileMultiPart: MultipartBody.Part? = null

    // Search
    var keyword = ""
    var filter = arrayListOf<String>()

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

    fun getLikeList() = viewModelScope.launch {
        when (val value = getLikeListUseCase()) {
            is Resource.Success<List<SongItem>> -> {
                _likeListData.value = value.data
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

    fun deletePlayList(songId: String) = viewModelScope.launch {
        deletePlayListUseCase(songId)
    }

    fun setCoverFile(uri: Uri, file: File) {
        viewModelScope.launch {
            _coverFile.value = uri
            val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            coverFileMultiPart =
                MultipartBody.Part.createFormData("coverFile", file.name, requestFile)
        }
    }

    fun setSongFile(uri: Uri, file: File) {
        viewModelScope.launch {
            _songFile.value = uri
            val requestFile = file.asRequestBody("audio/*".toMediaTypeOrNull())
            songFileMultiPart =
                MultipartBody.Part.createFormData("songFile", file.name, requestFile)
        }
    }

    fun uploadSong(songTitle: String, genre: String, moods: ArrayList<String>) {
        viewModelScope.launch {
            when (val value =
                uploadSongUseCase(
                    coverFileMultiPart,
                    songFileMultiPart,
                    moods,
                    genre,
                    songTitle
                )) {
                is Resource.Success<SongItem> -> {
                    Log.d("uploadSong", "uploadSong: ${value.data}")
                }
                is Resource.Error -> {
                    Log.e("uploadSong", "uploadSong: ${value.errorMessage}")
                }
            }
        }
    }
}