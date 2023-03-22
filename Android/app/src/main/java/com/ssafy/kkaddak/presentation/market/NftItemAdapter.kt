package com.ssafy.kkaddak.presentation.market

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.ItemNftBinding
import com.ssafy.kkaddak.domain.entity.market.NftItem

class NftItemAdapter : RecyclerView.Adapter<NftItemAdapter.NftItemViewHolder>() {

    private var items: List<NftItem> = listOf()
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
//            binding.ivNftItem.setImageResource(data.nftitem)
            Glide.with(binding.root)
                .load("https://images.chosun.com/resizer/bBg130MbE91hOsknQObn8WKEu6M=/600x600/smart/cloudfront-ap-northeast-1.images.arcpublishing.com/chosun/FKPYF7QGYFD7LH6SUQZMJWGGEI.png")
                .into(binding.ivNftItem)
//            Glide.with(binding.root).load(data.nftImagePath).into(binding.ivNftItem)

            binding.tvNftLike.text = data.likecount.toString()
            binding.tvNftCreator.text = data.nftCreator
            binding.tvNftSongTitle.text = data.nftSingTitle
            binding.tvNftActionDate.text = data.nftDeadline
            binding.tvNftActionPrice.text = String.format("%.2f", data.nftPrice)
        }
    }

    fun setNfts(nftItem: List<NftItem>) {
        this.items = nftItem
        notifyDataSetChanged()
    }
}