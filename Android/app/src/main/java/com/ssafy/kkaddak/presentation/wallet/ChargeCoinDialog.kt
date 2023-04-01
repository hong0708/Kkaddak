package com.ssafy.kkaddak.presentation.wallet

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import com.ssafy.kkaddak.databinding.DialogChargeCoinBinding

class ChargeCoinDialog(
    val activity: Activity,
    private val listener: SetWalletDialogInterface
) : Dialog(activity) {

    private lateinit var binding: DialogChargeCoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogChargeCoinBinding.inflate(layoutInflater)
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
            tvPaymentCancel.setOnClickListener {
                dismiss()
            }

            tvPaymentConfirm.setOnClickListener {
                listener.chargeCoin(etChargeAmount.text.toString().toDouble())
                dismiss()
            }
        }
    }
}