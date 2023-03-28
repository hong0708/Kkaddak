package com.ssafy.kkaddak.presentation.profile

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.ItemFollowerBinding
import com.ssafy.kkaddak.domain.entity.profile.FollowerItem

class FollowerAdapter(
    private val onItemClicked: (nickname: String) -> Unit,
    private val follow: (memberId: String) -> Unit,
    private val unfollow: (memberId: String) -> Unit
) : RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder>() {

    private var items: List<FollowerItem> = listOf()
    lateinit var binding: ItemFollowerBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_follower, parent, false
        )
        return FollowerViewHolder(binding, onItemClicked, follow, unfollow)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class FollowerViewHolder(
        private val binding: ItemFollowerBinding,
        private val onItemClicked: (nickname: String) -> Unit,
        private val follow: (memberId: String) -> Unit,
        private val unfollow: (memberId: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(data: FollowerItem) {
            binding.apply {
                user = data
                tvFollowState.apply {
                    if (data.isFollowing) {
                        setOnClickListener { unfollow(data.followerUuid) }
                        setBackgroundResource(R.drawable.bg_rect_indigo_to_han_purple_angle270_radius5)
                        text = "Unfollow"
                    } else {
                        setOnClickListener { follow(data.followerUuid) }
                        setBackgroundResource(R.drawable.bg_rect_bitter_sweet_to_neon_pink_radius5)
                        text = "Follow"
                    }
                }
                root.setOnClickListener {
                    onItemClicked(data.nickname)
                }
            }
        }
    }

    fun setList(followerItem: List<FollowerItem>) {
        this.items = followerItem
        notifyDataSetChanged()
    }
}