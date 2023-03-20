package com.ssafy.kkaddak.presentation.songlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.ItemSongBinding
import com.ssafy.kkaddak.domain.entity.song.SongItem

class SongAdapter(
    private val onItemClicked: (songId: Long) -> Unit
) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    private var items: List<SongItem> = listOf()
    lateinit var binding: ItemSongBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_song, parent, false
        )
        return SongViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class SongViewHolder(
        private val binding: ItemSongBinding,
        private val onItemClicked: (songId: Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: SongItem) {
            binding.song = data
//            binding.root.setOnClickListener {
//                onItemClicked(data.id)
//            }
        }
    }

    fun setSong(songItem: List<SongItem>) {
        this.items = songItem
        notifyDataSetChanged()
    }
}