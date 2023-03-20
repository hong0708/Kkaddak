package com.ssafy.kkaddak.presentation.market

import com.ssafy.kkaddak.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.kkaddak.databinding.ItemNftBinding

class NftItemAdapter : RecyclerView.Adapter<NftItemAdapter.NftItemViewHolder>() {

    private val items: ArrayList<NftItem> = ArrayList()
    lateinit var binding: ItemNftBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NftItemViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_nft, parent, false
        )

        return NftItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NftItemViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class NftItemViewHolder(
        val binding: ItemNftBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: NftItem) {
            binding.ivNftItem.setImageResource(data.nftitem)
            binding.tvNftLike.text = data.likecount.toString()
            binding.tvNftCreator.text = data.creator
            binding.tvNftSongTitle.text = data.songtitle
            binding.tvNftActionDate.text = data.date
            binding.tvNftActionPrice.text = String.format("%.2f", data.price)
        }
    }
    fun addItem(data: NftItem) {
        // 외부에서 item을 추가시킬 함수입니다.
        items.add(data)
    }
}