package com.ssafy.kkaddak.presentation.wallet

interface SetWalletDialogInterface {
    fun setWallet(walletAddress: String, privateKey: String)
    fun generateWallet()
}