package com.ssafy.kkaddak.presentation.profile

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import com.ssafy.kkaddak.databinding.DialogNftDetailBinding

class CreateNFTDialog(
    val activity: Activity,
) : Dialog(activity) {

    private lateinit var binding: DialogNftDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogNftDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(true)
        setCancelable(true)
    }
}