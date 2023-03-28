package com.ssafy.kkaddak.presentation.profile

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import com.ssafy.kkaddak.databinding.DialogCancelSubscribeBinding

class CancelSubscribeDialog(
    context: Context,
    private val artistId: String,
    private val listener: CancelSubscribeDialogListener
) : Dialog(context) {

    private lateinit var binding: DialogCancelSubscribeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogCancelSubscribeBinding.inflate(layoutInflater)
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
            tvCancel.setOnClickListener { dismiss() }
            tvConfirmCancelSubscribe.setOnClickListener {
                listener.onConfirmButtonClicked(artistId)
                dismiss()
            }
        }
    }
}