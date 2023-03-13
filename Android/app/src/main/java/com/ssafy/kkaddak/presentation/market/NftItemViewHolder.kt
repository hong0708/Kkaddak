package com.ssafy.kkaddak.presentation.market

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.kkaddak.R

class NftItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var iv_nft_item: ImageView
    var tv_nft_like_count: TextView
    var tv_nft_creator: TextView
    var tv_nft_song_title: TextView
    var tv_nft_action_date: TextView
    var tv_nft_action_price: TextView

    init {
        iv_nft_item = itemView.findViewById(R.id.iv_nft_item)
        tv_nft_like_count = itemView.findViewById(R.id.tv_nft_like)
        tv_nft_creator = itemView.findViewById(R.id.tv_nft_creator)
        tv_nft_song_title = itemView.findViewById(R.id.tv_nft_song_title)
        tv_nft_action_date = itemView.findViewById(R.id.tv_nft_action_date)
        tv_nft_action_price = itemView.findViewById(R.id.tv_nft_action_price)
    }

    fun onBind(data: NftItem) {
        iv_nft_item.setImageResource(data.nftitem)
        tv_nft_like_count.text = data.likecount.toString()
        tv_nft_creator.text = data.creator
        tv_nft_song_title.text = data.songtitle
        tv_nft_action_date.text = data.date
        tv_nft_action_price.text = String.format("%.2f", data.price)
    }
}