package com.ssafy.kkaddak.presentation.market

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.ItemUploadNftBinding
import com.ssafy.kkaddak.domain.entity.profile.ProfileNFTItem

class UploadNftItemAdapter(
//    private val onItemClicked: (nftId: BigInteger) -> Unit
) : RecyclerView.Adapter<UploadNftItemAdapter.UploadNftItemViewHolder>() {

    private var items: List<ProfileNFTItem> = listOf()
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

    fun setData(data: List<ProfileNFTItem>) {
        this.items = data
        notifyDataSetChanged()
    }

    class UploadNftItemViewHolder(
        val binding: ItemUploadNftBinding,
//        private val onItemClicked: (nftId: BigInteger) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ProfileNFTItem) {
            Glide.with(binding.root).load(data.nftImageUrl).into(binding.ivNftItem)
            binding.root.setOnClickListener {
                Log.d("clicked", data.tokenId.toString())
//                onItemClicked(data.tokenId)
            }
        }
    }
}