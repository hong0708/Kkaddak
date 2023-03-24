package com.ssafy.kkaddak.presentation.wallet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor() : ViewModel() {
    private val _balance: MutableLiveData<String> = MutableLiveData("")
    val balance: LiveData<String> = _balance

    fun updateBalance(newBalance: String) {
        _balance.value = newBalance
    }
}