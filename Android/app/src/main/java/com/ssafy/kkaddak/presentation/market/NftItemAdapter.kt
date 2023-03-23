package com.ssafy.kkaddak.presentation.market

import android.annotation.SuppressLint
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.common.util.BindingAdapters.setNormalImg
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
//            Glide.with(binding.root)
//                .load("https://images.chosun.com/resizer/bBg130MbE91hOsknQObn8WKEu6M=/600x600/smart/cloudfront-ap-northeast-1.images.arcpublishing.com/chosun/FKPYF7QGYFD7LH6SUQZMJWGGEI.png")
//                .into(binding.ivNftItem)
//            Glide.with(binding.root).load("http://j8d208.p.ssafy.io:8087/images"+ data.nftImagePath).into(binding.ivNftItem)
            binding.ivNftItem.setNormalImg(data.nftImagePath)
            // Get screen size in dp
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
            val screenHeightDp = displayMetrics.heightPixels / displayMetrics.density
            Log.d("screenDp", displayMetrics.widthPixels.toString())
            Log.d("screenDp", screenWidthDp.toString())
//            binding.cvMarketNft.layoutParams.width = (displayMetrics.widthPixels / 2 - 80).toInt()
//            Log.d("width1", binding.ivNftItem.width.toString())
            Log.d("width1", ((screenWidthDp - 60)/2).toString())
            Log.d("width2",
                ((screenWidthDp - 60)/2 * displayMetrics.density + 0.5f).toInt().toString()
            )
            binding.ivNftItem.layoutParams.width = ((screenWidthDp - 60)/2 * displayMetrics.density + 0.5f).toInt()
            binding.ivNftItem.layoutParams.height = ((screenWidthDp - 60)/2 * displayMetrics.density + 0.5f).toInt()
//            binding.ivNftItem.setLayoutParams(
//                ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    binding.ivNftItem.width
//                )
//            )

//            val heightInDp = 200 // Replace with the desired height in dp

//            val scale: Float = getResources().getDisplayMetrics().density
//            val heightInPixels = ((screenWidthDp - 60)/2 * displayMetrics.density + 0.5f).toInt()
//            imageView.setLayoutParams(
//                ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    heightInPixels
//                )
//            )

            if(binding.clTitle.layoutParams.width >= binding.clNftInfo.layoutParams.width) {
                Log.d("test", "시작")
                var params = binding.tvNftCreator.layoutParams as ConstraintLayout.LayoutParams
                params.endToStart = ConstraintLayout.LayoutParams.UNSET
                binding.tvNftCreator.layoutParams = params

                params = binding.tvContour.layoutParams as ConstraintLayout.LayoutParams
                params.startToEnd = ConstraintLayout.LayoutParams.UNSET
                params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                binding.tvContour.layoutParams = params
            }




            if (binding.clTitle.layoutParams.width >= displayMetrics.widthPixels / 2 - 80) {
                binding.tvNftCreator.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            }
            binding.apply {
                tvNftLike.text = data.cntLikeAcution.toString()
                tvNftCreator.text = data.nftCreator
                tvNftSongTitle.text = data.nftSingTitle
                val year = data.nftDeadline.substring(2, 4)
                val month = data.nftDeadline.substring(5, 7)
                val day = data.nftDeadline.substring(8, 10)
                tvNftActionDate.text = String.format("%s.%s.%s", year, month, day)
                tvNftActionPrice.text = String.format("%.2f", data.nftPrice)
                if(data.isLike){
                    ivNftLike.setImageResource(R.drawable.ic_market_like_selected)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNfts(nftItem: List<NftItem>) {
        this.items = nftItem
        notifyDataSetChanged()
    }
}