package com.ssafy.kkaddak.presentation.market

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.ItemHistoryBinding
import com.ssafy.kkaddak.domain.entity.market.HistoryItem

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private var items: List<HistoryItem> = listOf()
    lateinit var binding: ItemHistoryBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_history, parent, false
        )
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setDatas(historyItem: List<HistoryItem>) {
        this.items = historyItem
        notifyDataSetChanged()
    }

    class HistoryViewHolder(
        private val binding: ItemHistoryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: HistoryItem) {
            binding.apply {
                tvHistoryDate.text = data.timestamp.replace("-", ".").substring(0, 10)
                tvHistoryPrice.text = String.format("%.1f", data.price.toDouble() / 100000000)
            }
        }
    }
}