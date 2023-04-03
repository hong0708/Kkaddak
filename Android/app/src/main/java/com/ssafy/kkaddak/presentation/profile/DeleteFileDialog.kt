package com.ssafy.kkaddak.presentation.profile

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.DialogDeleteFileBinding

class DeleteFileDialog(
    context: Context,
    private val listener: FileDeleteDialogListener
) : Dialog(context) {

    private lateinit var binding:  DialogDeleteFileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_delete_file,
            null, false
        )

        setContentView(binding.root)

        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        binding.apply {
            tvConfirmDeleteFile.setOnClickListener {
                listener.onConfirmButtonClicked()
                dismiss()
            }
            tvCancelDeleteFile.setOnClickListener {
                dismiss()
            }
        }
    }
}