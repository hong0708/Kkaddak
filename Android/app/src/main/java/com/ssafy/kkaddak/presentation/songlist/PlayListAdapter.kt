package com.ssafy.kkaddak.presentation.songlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.ItemPlaylistSongBinding
import com.ssafy.kkaddak.domain.entity.song.SongItem

class PlayListAdapter(
    private val onItemClicked: (songId: String) -> Unit,
    private val itemDeleted: (songId: String) -> Unit
) : RecyclerView.Adapter<PlayListAdapter.SongViewHolder>() {

    private var items: List<SongItem> = listOf()
    lateinit var binding: ItemPlaylistSongBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_playlist_song, parent, false
        )
        return SongViewHolder(binding, onItemClicked, itemDeleted)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class SongViewHolder(
        private val binding: ItemPlaylistSongBinding,
        private val onItemClicked: (songId: String) -> Unit,
        private val itemDeleted: (songId: String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: SongItem) {
            binding.song = data
            binding.ivSongDelete.setOnClickListener {
                itemDeleted(data.songId)
            }
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