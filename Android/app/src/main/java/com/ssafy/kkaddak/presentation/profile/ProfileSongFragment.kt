package com.ssafy.kkaddak.presentation.profile

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.kkaddak.ApplicationClass
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.FragmentProfileSongBinding
import com.ssafy.kkaddak.presentation.base.BaseFragment
import com.ssafy.kkaddak.presentation.market.GridSpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileSongFragment :
    BaseFragment<FragmentProfileSongBinding>(R.layout.fragment_profile_song) {

    private val profileViewModel by activityViewModels<ProfileViewModel>()
    private val profileSongAdapter by lazy { ProfileSongAdapter(this::getSongDetail) }

    override fun initView() {
        setProfileSong()
    }

    private fun setProfileSong() {
        binding.rvProfileSong.apply {
            adapter = profileSongAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
            addItemDecoration(GridSpaceItemDecoration(3, 1))
        }
        profileViewModel.profileSongData.observe(viewLifecycleOwner) { response ->
            response?.let { profileSongAdapter.setSong(it) }
        }
        profileViewModel.getProfileSong(ApplicationClass.preferences.nickname!!)
    }

    private fun getSongDetail(songId: String) {
        navigate(
            ProfileFragmentDirections.actionProfileFragmentToSongDetailFragment(songId)
        )
    }
}