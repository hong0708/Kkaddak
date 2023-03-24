package com.ssafy.kkaddak.presentation.wallet

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import com.ssafy.kkaddak.common.util.WalletFunction
import com.ssafy.kkaddak.databinding.DialogSetWalletBinding

class ChargeDialog(val activity: Activity) : Dialog(activity) {

    private lateinit var binding: DialogSetWalletBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogSetWalletBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        initListener()
    }

    private fun initListener() {
        binding.apply {
            ivCloseWalletSetDialog.setOnClickListener {
                dismiss()
            }

            // 지갑 불러오기
            tvImportWallet.setOnClickListener {
                WalletFunction().insertUserWallet(
                    etWalletAddress.text.toString(),
                    etWalletKey.text.toString()
                )

                WalletFragment().getBalance()
            }

            // 지갑 생성
            tvMakeWallet.setOnClickListener {
                WalletFunction().generateWallet()
            }

            WalletFragment().getBalance()
        }
    }
}