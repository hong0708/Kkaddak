package com.ssafy.kkaddak.presentation.wallet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.ItemRecentTransactionBinding
import com.ssafy.kkaddak.domain.entity.wallet.RecentTransactionItem

class RecentTransactionListAdapter :
    RecyclerView.Adapter<RecentTransactionListAdapter.RecentTransactionListHolder>() {

    private var items: List<RecentTransactionItem> = listOf()
    lateinit var binding: ItemRecentTransactionBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentTransactionListHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_recent_transaction,
            parent,
            false
        )
        return RecentTransactionListHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentTransactionListHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class RecentTransactionListHolder(
        private val binding: ItemRecentTransactionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: RecentTransactionItem) {
            binding.apply { }
        }
    }

    fun setData(data: List<RecentTransactionItem>){
        this.items = data
        notifyDataSetChanged()
    }
}