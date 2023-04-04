package com.ssafy.kkaddak.presentation.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kkaddak.ApplicationClass
import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.data.remote.datasource.profile.NFTImageResponse
import com.ssafy.kkaddak.domain.entity.profile.FollowerItem
import com.ssafy.kkaddak.domain.entity.profile.ProfileItem
import com.ssafy.kkaddak.domain.entity.song.SongItem
import com.ssafy.kkaddak.domain.usecase.profile.*
import com.ssafy.kkaddak.domain.usecase.user.CheckDuplicationUseCase
import com.ssafy.kkaddak.domain.usecase.user.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val getProfileSongUseCase: GetProfileSongUseCase,
    private val deleteMySongUseCase: DeleteMySongUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val requestFollowUseCase: RequestFollowUseCase,
    private val getFollowInfoUseCase: GetFollowInfoUseCase,
    private val uploadNFTImageUseCase: UploadNFTImageUseCase,
    private val uploadThumbnailUseCase: UploadThumbnailUseCase,
    private val editProfileUseCase: EditProfileUseCase,
    private val checkDuplicationUseCase: CheckDuplicationUseCase,
    private val changeSongStateUseCase: ChangeSongStateUseCase
) : ViewModel() {

    private val _profileData: MutableLiveData<ProfileItem?> = MutableLiveData()
    val profileData: LiveData<ProfileItem?> = _profileData

    private val _profileSongData: MutableLiveData<List<SongItem>?> = MutableLiveData()
    val profileSongData: LiveData<List<SongItem>?> = _profileSongData

    private val _followers: MutableLiveData<List<FollowerItem>?> = MutableLiveData()
    val followers: LiveData<List<FollowerItem>?> = _followers

    private val _followings: MutableLiveData<List<FollowerItem>?> = MutableLiveData()
    val followings: LiveData<List<FollowerItem>?> = _followings

    private val _nftImageUrl: MutableLiveData<String?> = MutableLiveData("")
    val nftImageUrl: LiveData<String?> = _nftImageUrl

    private val _nickname: MutableLiveData<String> = MutableLiveData()
    val nickname: MutableLiveData<String> = _nickname

    private val _isDuplicate: MutableLiveData<Boolean?> = MutableLiveData(true)
    val isDuplicate: MutableLiveData<Boolean?> = _isDuplicate

    private val _profileImgUri: MutableLiveData<Uri?> = MutableLiveData()
    val profileImgUri: MutableLiveData<Uri?> = _profileImgUri

    private val _profileImgStr: MutableLiveData<String?> = MutableLiveData()
    val profileImgStr: MutableLiveData<String?> = _profileImgStr

    private val _songStateChange: MutableLiveData<Boolean?> = MutableLiveData(false)
    val songStateChange: MutableLiveData<Boolean?> = _songStateChange

    var profileImgMultiPart: MultipartBody.Part? = null

    fun setProfileImg(uri: Uri, file: File) {
        _profileImgUri.value = uri
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        profileImgMultiPart =
            MultipartBody.Part.createFormData("profileImg", file.name, requestFile)
    }

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

    fun getFollowers(lastId: Int, limit: Int) = viewModelScope.launch {
        when (val value = getFollowInfoUseCase.getFollowers(lastId, limit)) {
            is Resource.Success<List<FollowerItem>> -> {
                _followers.value = value.data
            }
            is Resource.Error -> {
                Log.e("getFollowers", "getFollowers: ${value.errorMessage}")
            }
        }
    }

    fun getFollowings(lastId: Int, limit: Int) = viewModelScope.launch {
        when (val value = getFollowInfoUseCase.getFollowings(lastId, limit)) {
            is Resource.Success<List<FollowerItem>> -> {
                _followings.value = value.data
            }
            is Resource.Error -> {
                Log.e("getFollowings", "getFollowings: ${value.errorMessage}")
            }
        }
    }

    fun uploadThumbnail(nftImageUrl: String) = viewModelScope.launch {
        uploadThumbnailUseCase(nftImageUrl)
    }

    // 프로필 이미지를 바꿔 등록
    fun updateProfile(nickname: String) = viewModelScope.launch {
        editProfileUseCase(true, nickname, profileImgMultiPart)
        ApplicationClass.preferences.nickname = nickname
    }

    // 프로필 이미지를 바꾸지 않고 등록
    fun updateProfileWithExistingImg(nickname: String) = viewModelScope.launch {
        editProfileUseCase(false, nickname, null)
        ApplicationClass.preferences.nickname = nickname
    }

    // 프로필 이미지를 지우고 등록
    fun updateProfileWithoutImg(nickname: String) = viewModelScope.launch {
        editProfileUseCase(true, nickname, null)
        ApplicationClass.preferences.nickname = nickname
    }

    suspend fun checkDuplication(nickname: String) =
        viewModelScope.async {
            when (val value = checkDuplicationUseCase(nickname)) {
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

    fun uploadNFTImage(songId: String, nftImg: MultipartBody.Part?) = viewModelScope.launch {
        when (val value = uploadNFTImageUseCase.invoke(songId, nftImg)) {
            is Resource.Success<NFTImageResponse> -> {
                _nftImageUrl.value = value.data.nftImageUrl
            }
            is Resource.Error -> {
                Log.e("uploadNFTImage", "uploadNFTImage: ${value.errorMessage}")
            }
        }
    }

    fun changeSongState(songStatus: String, songUUID: String) = viewModelScope.launch {
        when (val value = changeSongStateUseCase.invoke(songStatus, songUUID)) {
            is Resource.Success<Boolean> -> {
                _songStateChange.value = value.data
            }
            is Resource.Error -> {
                Log.e("changeSongState", "changeSongState: ${value.errorMessage}")
            }
        }
    }

    fun resetSongState() {
        _songStateChange.value = false
    }
}