package com.ssafy.kkaddak.presentation.wallet

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.kkaddak.ApplicationClass
import com.ssafy.kkaddak.data.remote.Resource
import com.ssafy.kkaddak.domain.entity.wallet.RecentTransactionItem
import com.ssafy.kkaddak.domain.usecase.user.RegisterWalletAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val registerWalletAccountUseCase: RegisterWalletAccountUseCase
) : ViewModel() {

    private val _recentTransactionListData: MutableLiveData<List<RecentTransactionItem>?> =
        MutableLiveData()
    val recentTransactionListData: LiveData<List<RecentTransactionItem>?> =
        _recentTransactionListData

    fun registerWalletAccount(walletAccount: String) = viewModelScope.launch {
        when (val value = registerWalletAccountUseCase(walletAccount)) {
            is Resource.Success<Boolean> -> {}
            is Resource.Error -> {
                ApplicationClass.preferences.walletAddress = ""
                ApplicationClass.preferences.privateKey = ""
                Log.e("registerWalletAccount", "registerWalletAccount: ${value.errorMessage}")
            }
        }
    }

    fun updateRecentTransactionListData(data: List<RecentTransactionItem>) {
        _recentTransactionListData.value = data
    }
}