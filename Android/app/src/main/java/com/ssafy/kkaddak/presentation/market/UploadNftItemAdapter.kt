package com.ssafy.kkaddak.presentation.market

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.ItemUploadNftBinding
import com.ssafy.kkaddak.domain.entity.market.UploadNftItem

class UploadNftItemAdapter : RecyclerView.Adapter<UploadNftItemAdapter.UploadNftItemViewHolder>() {

    private var items: ArrayList<UploadNftItem> = ArrayList()
    lateinit var binding: ItemUploadNftBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadNftItemViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_upload_nft, parent, false
        )

        return UploadNftItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UploadNftItemViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class UploadNftItemViewHolder(
        val binding: ItemUploadNftBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: UploadNftItem) {
            Glide.with(binding.root).load(data.nftImagePath).into(binding.ivNftItem)
        }
    }
}