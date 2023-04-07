package com.ssafy.kkaddak.presentation.wallet

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.WindowManager
import android.widget.TextView
import com.ssafy.kkaddak.ApplicationClass
import com.ssafy.kkaddak.common.util.WalletFunction
import com.ssafy.kkaddak.databinding.DialogWalletInfoBinding

class WalletInfoDialog(
    val activity: Activity
) : Dialog(activity) {

    private lateinit var binding: DialogWalletInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogWalletInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        initListener()
        initView()
    }

    private fun initView() {
        binding.apply {
            if (ApplicationClass.preferences.walletAddress != ""
                && ApplicationClass.preferences.privateKey != ""
            ) {
                tvWalletAddressInfo.text = String(
                    ApplicationClass.keyStore.decryptData(
                        WalletFunction().decode(ApplicationClass.preferences.walletAddress.toString())
                    )
                )
                textViewMovementMethod(tvWalletAddressInfo)

                tvWalletKeyInfo.text = String(
                    ApplicationClass.keyStore.decryptData(
                        WalletFunction().decode(ApplicationClass.preferences.privateKey.toString())
                    )
                )
                textViewMovementMethod(tvWalletKeyInfo)
            }
        }
    }

    private fun initListener() {
        binding.apply {
            ivCloseWalletInfoDialog.setOnClickListener { dismiss() }
        }
    }

    private fun textViewMovementMethod(textView: TextView) {
        textView.movementMethod = ScrollingMovementMethod.getInstance()
        textView.setHorizontallyScrolling(true)
        textView.isFocusable = true // 포커스 가능 상태로 변경
        textView.isFocusableInTouchMode = true
        textView.requestFocus() // 포커스 요청
    }
}