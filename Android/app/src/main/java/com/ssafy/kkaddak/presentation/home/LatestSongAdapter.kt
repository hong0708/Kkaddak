package com.ssafy.kkaddak.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.ItemHomeNewSongBinding
import com.ssafy.kkaddak.domain.entity.song.SongItem

class LatestSongAdapter(
    private val onItemClicked: (songId: String) -> Unit
) : RecyclerView.Adapter<LatestSongAdapter.ViewHolder>() {

    private var items: List<SongItem> = listOf()
    lateinit var binding: ItemHomeNewSongBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_home_new_song, parent, false
        )
        return ViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(
        private val binding: ItemHomeNewSongBinding,
        private val onItemClicked: (songId: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: SongItem) {
            binding.song = data
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