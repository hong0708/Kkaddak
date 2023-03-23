package com.ssafy.kkaddak.presentation.market

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.market.NftItem
import com.ssafy.kkaddak.domain.usecase.market.GetAllNftsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val getAllNftsUseCase: GetAllNftsUseCase
) : ViewModel() {

    private val _nftListData: MutableLiveData<List<NftItem>?> = MutableLiveData()
    val nftListData: LiveData<List<NftItem>?> = _nftListData

    fun getAllNfts(lastId: Long, limit: Long, onlySelling: Boolean) = viewModelScope.launch {
        when (val value = getAllNftsUseCase(lastId, limit, onlySelling)) {
            is Resource.Success<List<NftItem>> -> {
                _nftListData.value = value.data
            }
            is Resource.Error -> {
                Log.e("getAllNfts", "getAllNfts: ${value.errorMessage}")
            }
        }
    }

}