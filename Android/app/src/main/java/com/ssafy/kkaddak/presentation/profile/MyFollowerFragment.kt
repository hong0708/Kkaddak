package com.ssafy.kkaddak.presentation.profile

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.FragmentMyFollowerBinding
import com.ssafy.kkaddak.presentation.MainActivity
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyFollowerFragment :
    BaseFragment<FragmentMyFollowerBinding>(R.layout.fragment_my_follower) {

    private val followerAdapter by lazy {
        FollowerAdapter(
            this::getProfile,
            this::follow,
            this::unfollow
        )
    }
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
        binding.ivBack.setOnClickListener { popBackStack() }
    }

    private fun initRecyclerView() {
        binding.rvFollowing.apply {
            adapter = followerAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
        profileViewModel.followers.observe(viewLifecycleOwner) { response ->
            response?.let { followerAdapter.setList(it) }
        }
        profileViewModel.getFollowers(-1, 100)
    }

    private fun getProfile(nickname: String) {
        navigate(
            MyFollowerFragmentDirections.actionMyFollowerFragmentToOtherProfileFragment(
                nickname
            )
        )
    }

    private fun follow(memberId: String) {
        profileViewModel.followArtist(memberId)
    }

    private fun unfollow(memberId: String) {
        profileViewModel.unfollowArtist(memberId)
    }
}