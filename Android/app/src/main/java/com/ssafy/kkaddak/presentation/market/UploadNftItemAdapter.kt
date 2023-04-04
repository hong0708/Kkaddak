package com.ssafy.kkaddak.presentation.market

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.common.util.BindingAdapters.setNormalImg
import com.ssafy.kkaddak.databinding.ItemUploadNftBinding
import com.ssafy.kkaddak.domain.entity.profile.ProfileNFTItem
import java.math.BigInteger

class UploadNftItemAdapter(
    private val onItemClicked: (nftId: BigInteger) -> Unit
) : RecyclerView.Adapter<UploadNftItemAdapter.UploadNftItemViewHolder>() {

    private var items: List<ProfileNFTItem> = listOf()
    lateinit var binding: ItemUploadNftBinding
    var selectPosition = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadNftItemViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_upload_nft, parent, false
        )

        return UploadNftItemViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: UploadNftItemViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setData(data: List<ProfileNFTItem>) {
        this.items = data
        notifyDataSetChanged()
    }

    inner class UploadNftItemViewHolder(
        val binding: ItemUploadNftBinding,
        private val onItemClicked: (nftId: BigInteger) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ProfileNFTItem) {
            binding.ivNftItem.setNormalImg(data.nftImageUrl.toUri())
            if (selectPosition == adapterPosition) {
                binding.ivNftItem.alpha = 1F
            } else {
                binding.ivNftItem.alpha = 0.2F
            }

            binding.root.setOnClickListener {
                Log.d("clicked", data.tokenId.toString())
                val beforePosition = selectPosition
                selectPosition = position

                notifyItemChanged(beforePosition)
                notifyItemChanged(selectPosition)
                onItemClicked(data.tokenId)
            }
        }
    }
}