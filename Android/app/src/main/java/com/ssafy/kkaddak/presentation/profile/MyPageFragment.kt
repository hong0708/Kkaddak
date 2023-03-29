package com.ssafy.kkaddak.presentation.profile

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.kkaddak.ApplicationClass
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.FragmentMypageBinding
import com.ssafy.kkaddak.presentation.MainActivity
import com.ssafy.kkaddak.presentation.base.BaseFragment
import com.ssafy.kkaddak.presentation.songlist.SongViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {

    private val myPageHistoryAdapter by lazy { MyPageHistoryAdapter(this::getSongDetail) }
    private val myPageLikeAdapter by lazy { MyPageLikeAdapter(this::getSongDetail) }
    private val songViewModel by activityViewModels<SongViewModel>()
    private val profileViewModel by activityViewModels<ProfileViewModel>()

    override fun initView() {
        (activity as MainActivity).hideBottomNavigation(true)
        initRecyclerView()
        initListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).hideBottomNavigation(false)
    }

    private fun initListener() {
        binding.apply {
            tvEntireHistory.setOnClickListener {
                navigate(MyPageFragmentDirections.actionMyPageFragmentToPlayListFragment())
            }
            tvEntireFavorite.setOnClickListener {
                navigate(MyPageFragmentDirections.actionMyPageFragmentToLikeListFragment())
            }
            ivBack.setOnClickListener {
                popBackStack()
            }
            tvLogout.setOnClickListener {
                profileViewModel.requestLogout()
                ApplicationClass.preferences.apply {
                    accessToken = null
                    refreshToken = null
                    isLoggedIn = false
                }
                navigate(MyPageFragmentDirections.actionMyPageFragmentToSplashFragment())
                Toast.makeText(requireContext(), "로그아웃되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvFavorite.apply {
            adapter = myPageLikeAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }
        songViewModel.likeListData.observe(viewLifecycleOwner) { response ->
            response?.let { myPageLikeAdapter.setSong(it.subList(0, minOf(5, it.size))) }
        }
        songViewModel.getLikeList()

        binding.rvHistory.apply {
            adapter = myPageHistoryAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }
        songViewModel.playListData.observe(viewLifecycleOwner) { response ->
            response?.let { myPageHistoryAdapter.setSong(it.subList(0, minOf(5, it.size))) }
        }
        songViewModel.getPlayList()
    }

    private fun getSongDetail(songId: String) {
        navigate(
            MyPageFragmentDirections.actionMyPageFragmentToSongDetailFragment(
                songId
            )
        )
    }
}