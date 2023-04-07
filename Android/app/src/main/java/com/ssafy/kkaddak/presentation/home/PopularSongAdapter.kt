package com.ssafy.kkaddak.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.ItemHomePopularSongBinding
import com.ssafy.kkaddak.domain.entity.song.SongItem

class PopularSongAdapter(
    private val onItemClicked: (songId: String) -> Unit
) : RecyclerView.Adapter<PopularSongAdapter.ViewHolder>() {

    private var items: List<SongItem> = listOf()
    lateinit var binding: ItemHomePopularSongBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_home_popular_song, parent, false
        )
        return ViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(
        private val binding: ItemHomePopularSongBinding,
        private val onItemClicked: (songId: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: SongItem, position: Int) {
            binding.song = data
            (position + 1).toString().also { binding.tvRanking.text = it }
            binding.root.setOnClickListener {
                onItemClicked(data.songId)
            }
        }
    }

    fun setSong(songItem: List<SongItem>) {
        this.items = songItem
        notifyDataSetChanged()
    }
}