package com.ssafy.kkaddak.presentation.wallet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.kkaddak.ApplicationClass
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.common.util.WalletFunction
import com.ssafy.kkaddak.databinding.ItemRecentTransactionBinding
import com.ssafy.kkaddak.domain.entity.wallet.RecentTransactionItem
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

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
            binding.apply {
                tvTypeHistory.text = data.transferType
                tvAmountKat.text = (data.amount.toFloat() / 100000000).toString()
                val format = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
                tvDateHistory.text = format.format(Date(data.timestamp.toLong() * 1000))

                if (data.transferType == "NFT") {
                    if (data.sender ==
                        String(
                            ApplicationClass.keyStore.decryptData(
                                WalletFunction().decode(ApplicationClass.preferences.walletAddress.toString())
                            )
                        )
                    ) {
                        ivTypeHistory.setImageResource(R.drawable.ic_down_caret)
                        tvSignKat.text = "-"
                        tvTypeHistory.text = "NFT 구매"
                    } else {
                        ivTypeHistory.setImageResource(R.drawable.ic_up_caret)
                        tvSignKat.text = "+"
                        tvTypeHistory.text = "NFT 판매"
                    }
                } else if (data.transferType == "구독") {
                    if (data.sender ==
                        String(
                            ApplicationClass.keyStore.decryptData(
                                WalletFunction().decode(ApplicationClass.preferences.walletAddress.toString())
                            )
                        )
                    ) {
                        ivTypeHistory.setImageResource(R.drawable.ic_down_caret)
                        tvSignKat.text = "-"
                    } else {

                        ivTypeHistory.setImageResource(R.drawable.ic_up_caret)
                        tvSignKat.text = "+"
                        tvTypeHistory.text = "구독 후원"
                    }
                } else {
                    ivTypeHistory.setImageResource(R.drawable.ic_up_caret)
                    tvSignKat.text = "+"
                }
            }
        }
    }

    fun setData(data: List<RecentTransactionItem>) {
        this.items = data.reversed()
        notifyDataSetChanged()
    }
}