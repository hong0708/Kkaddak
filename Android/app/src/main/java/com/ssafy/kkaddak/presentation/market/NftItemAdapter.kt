package com.ssafy.kkaddak.presentation.market

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.common.util.BindingAdapters.setNormalImg
import com.ssafy.kkaddak.databinding.ItemNftBinding
import com.ssafy.kkaddak.domain.entity.market.NftItem


class NftItemAdapter(
    private val onItemClicked: (nftItem: NftItem) -> Unit
) : RecyclerView.Adapter<NftItemAdapter.NftItemViewHolder>() {

    private var items: List<NftItem> = listOf()
    lateinit var binding: ItemNftBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NftItemViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_nft, parent, false
        )
        return NftItemViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: NftItemViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size
    @SuppressLint("NotifyDataSetChanged")
    fun setNfts(nftItem: List<NftItem>) {
        this.items = nftItem
        notifyDataSetChanged()
    }

    class NftItemViewHolder(
        private val binding: ItemNftBinding,
        private val onItemClicked: (nftItem: NftItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: NftItem) {
            binding.apply {
                ivNftItem.setNormalImg(data.nftImagePath)
                tvNftLike.text = data.cntLikeAcution.toString()
                tvNftCreator.text = data.nftCreator
                tvNftSongTitle.text = data.nftSongTitle
                val year = data.nftCreateDate.substring(2, 4)
                val month = data.nftCreateDate.substring(5, 7)
                val day = data.nftCreateDate.substring(8, 10)
                tvNftActionDate.text = String.format("%s.%s.%s", year, month, day)
                tvNftActionPrice.text = String.format("%.2f", data.nftPrice)
                if (data.isLike) {
                    ivNftLike.setImageResource(R.drawable.ic_market_like_selected)
                }
            }

            // 이미지 크기 조절
            // Get screen size in dp
            val displayMetrics = Resources.getSystem().displayMetrics
            val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
            val imageSize = ((screenWidthDp - 50) / 2 * displayMetrics.density + 0.5f).toInt()
            binding.ivNftItem.layoutParams.width = imageSize
            binding.ivNftItem.layoutParams.height = imageSize

            // 이름과 제목 길이가 이미지크기를 벗어나면 제목을 이름 하단에 표시한다.
            binding.clTitle.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            val measuredWidth = binding.clTitle.measuredWidth
            if (measuredWidth >= imageSize) {
                var params = binding.tvNftCreator.layoutParams as ConstraintLayout.LayoutParams
                params.endToStart = ConstraintLayout.LayoutParams.UNSET
                params.bottomToTop = binding.tvContour.id
                binding.tvNftCreator.layoutParams = params

                params = binding.tvContour.layoutParams as ConstraintLayout.LayoutParams
                params.startToEnd = ConstraintLayout.LayoutParams.UNSET
                params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                params.topToBottom = binding.tvNftCreator.id
                binding.tvContour.layoutParams = params
            }

            binding.root.setOnClickListener {
                onItemClicked(data)
            }
        }
    }
}