package com.ssafy.kkaddak.presentation.profile

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import com.ssafy.kkaddak.databinding.DialogDeleteRejectedSongBinding

class DeleteRejectedSongDialog(
    context: Context,
    private val songId: String,
    private val listener: DeleteRejectedSongDialogListener
) : Dialog(context) {

    private lateinit var binding: DialogDeleteRejectedSongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogDeleteRejectedSongBinding.inflate(layoutInflater)
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
            tvCancelDeleteSong.setOnClickListener { dismiss() }
            tvConfirmDeleteSong.setOnClickListener {
                listener.onConfirmButtonClicked(songId)
                dismiss()
            }
        }
    }
}