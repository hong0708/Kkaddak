package com.ssafy.kkaddak.presentation.songlist

import android.content.Context
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.FragmentSongListBinding
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SongListFragment :
    BaseFragment<FragmentSongListBinding>(R.layout.fragment_song_list) {

    private val songAdapter by lazy { SongAdapter(this::getSongDetail) }
    private val songViewModel by activityViewModels<SongViewModel>()
    private val filterList: ArrayList<TextView> = arrayListOf()

    override fun initView() {
        binding.etSearch.setText("")
        initRecyclerView()
        initListener()
        initFilter()
    }

    override fun onResume() {
        super.onResume()
        binding.etSearch.setText("")
    }

    private fun initFilter() {

    }

    private fun initListener() {
        binding.etSearch.apply {
            setOnEditorActionListener { word, id, _ ->
                var handled = false
                if (id == EditorInfo.IME_ACTION_SEARCH) {
                    songViewModel.keyword = this.text.toString()
                    songViewModel.searchMusic(songViewModel.keyword, songViewModel.filter.joinToString(separator = ","))
                    this.clearFocus()
                    this.isFocusable = false
//                    this.setText("")
                    this.isFocusableInTouchMode = true
                    this.isFocusable = true
                    val imm =
                        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
                    handled = true
                }
                return@setOnEditorActionListener handled
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvSongList.apply {
            adapter = songAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
        songViewModel.songListData.observe(viewLifecycleOwner) { response ->
            response?.let { songAdapter.setSong(it) }
        }
        songViewModel.getSongs()
    }

    private fun getSongDetail(songId: String) {
        navigate(
            SongListFragmentDirections.actionSongListFragmentToSongDetailFragment(
                songId
            )
        )
    }
}