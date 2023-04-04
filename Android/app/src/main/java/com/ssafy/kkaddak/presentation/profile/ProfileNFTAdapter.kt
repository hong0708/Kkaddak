package com.ssafy.kkaddak.presentation.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.ItemProfileNftBinding
import com.ssafy.kkaddak.domain.entity.profile.ProfileNFTItem
import java.math.BigInteger

class ProfileNFTAdapter(
    private val isMine: Boolean,
    private val onItemClicked: (nftId: BigInteger, isMine: Boolean) -> Unit
) : RecyclerView.Adapter<ProfileNFTAdapter.ViewHolder>() {

    private var items: List<ProfileNFTItem> = listOf()
    lateinit var binding: ItemProfileNftBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_profile_nft, parent, false
        )
        return ViewHolder(binding, onItemClicked, /*onRejectBadgeClicked,*/ isMine)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(
        private val binding: ItemProfileNftBinding,
        private val onItemClicked: (nftId: BigInteger, isMine: Boolean) -> Unit,
        private val isMine: Boolean
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ProfileNFTItem) {
            binding.apply {
                nft = data
                root.setOnClickListener { onItemClicked(data.tokenId, isMine) }
            }
        }
    }

    fun setData(data: List<ProfileNFTItem>) {
        this.items = data
        notifyDataSetChanged()
    }
}