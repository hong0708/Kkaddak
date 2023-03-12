package com.ssafy.kkaddak.presentation.market

import com.ssafy.kkaddak.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class NftItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listData: ArrayList<NftItem> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_nft, parent, false)
        return NftItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NftItemViewHolder).onBind(listData[position])

    }

    override fun getItemCount(): Int {
        return listData.size
    }

    fun addItem(data: NftItem) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data)
    }
}