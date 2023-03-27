package com.ssafy.kkaddak.presentation.profile

import android.view.WindowManager
import android.widget.Toast
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
    BaseFragment<FragmentProfileSongBinding>(R.layout.fragment_profile_song),
    DeleteRejectedSongDialogListener {

    private val profileViewModel by activityViewModels<ProfileViewModel>()
    private val profileSongAdapter by lazy {
        ProfileSongAdapter(
            this::getSongDetail,
            this::deleteMySong
        )
    }

    override fun initView() {
        setProfileSong()
    }

    override fun onResume() {
        super.onResume()
        profileViewModel.profileSongData.observe(viewLifecycleOwner) { response ->
            response?.let { profileSongAdapter.setSong(it) }
        }
        profileViewModel.getProfileSong(ApplicationClass.preferences.nickname!!)
    }

    private fun setProfileSong() {
        binding.rvProfileSong.apply {
            adapter = profileSongAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
            addItemDecoration(GridSpaceItemDecoration(3, 3))
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

    private fun deleteMySong(songId: String) {
        val dialog = DeleteRejectedSongDialog(requireContext(), songId, this)
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onConfirmButtonClicked(songId: String) {
        profileViewModel.deleteMySong(songId)
        profileViewModel.getProfileSong(ApplicationClass.preferences.nickname!!)
        Toast.makeText(requireContext(), "음악이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
        profileViewModel.getProfileSong(ApplicationClass.preferences.nickname!!)
    }
}