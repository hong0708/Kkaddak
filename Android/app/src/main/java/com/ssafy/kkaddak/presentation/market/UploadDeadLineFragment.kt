package com.ssafy.kkaddak.presentation.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.ssafy.kkaddak.R

class UploadDeadLineFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //false로 설정해 주면 화면밖 혹은 뒤로가기 버튼시 다이얼로그라 dismiss 되지 않는다.
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_upload_nft_deadline, container, false)
    }

    private fun setDate(np: NumberPicker?, values: Array<String>) {
        np?.displayedValues = values
        np?.minValue = 0
        np?.maxValue = values.size - 1
        np?.value = 0 // Want to show "a" in number picker

        np?.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        np?.wrapSelectorWheel = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnOk = view?.findViewById<TextView>(R.id.tv_confirm_nft_item)
        val btnCancel = view?.findViewById<TextView>(R.id.tv_cancel_nft_item)
        val npYear = view?.findViewById<NumberPicker>(R.id.np_year)
        val npMonth = view?.findViewById<NumberPicker>(R.id.np_month)
        val npDay = view?.findViewById<NumberPicker>(R.id.np_day)
        val years = arrayOf("2023년", "2024년", "2025년", "2026년", "2027년", "2028년", "2029년", "2030년")
        val months =
            arrayOf("1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월")
        val days = Array(31, {""})

        for (day in 0..30) {
            days.set(day, (day + 1).toString() + "일")
        }

        setDate(npYear, years)
        setDate(npMonth, months)
        setDate(npDay, days)

        btnOk?.setOnClickListener {  }
        btnCancel?.setOnClickListener {  }
    }

}