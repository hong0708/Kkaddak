package com.ssafy.kkaddak.presentation.profile

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.FragmentMyFollowingBinding
import com.ssafy.kkaddak.presentation.MainActivity
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyFollowingFragment :
    BaseFragment<FragmentMyFollowingBinding>(R.layout.fragment_my_following) {

    private val followingAdapter by lazy {
        FollowingAdapter(
            this::getProfile,
            this::follow,
            this::unfollow
        )
    }
    private val profileViewModel by activityViewModels<ProfileViewModel>()

    override fun initView() {
        (activity as MainActivity).HideBottomNavigation(true)
        initRecyclerView()
        initListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).HideBottomNavigation(false)
    }

    private fun initListener() {
        binding.ivBack.setOnClickListener { popBackStack() }
    }

    private fun initRecyclerView() {
        binding.rvFollowing.apply {
            adapter = followingAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
        profileViewModel.followings.observe(viewLifecycleOwner) { response ->
            response?.let { followingAdapter.setList(it) }
        }
        profileViewModel.getFollowings(-1, 100)
    }

    private fun getProfile(nickname: String) {
        navigate(
            MyFollowingFragmentDirections.actionMyFollowingFragmentToOtherProfileFragment(
                nickname
            )
        )
    }

    private fun follow(memberId: String) {
        profileViewModel.followArtist(memberId)
        profileViewModel.getFollowers(-1, 100)
    }

    private fun unfollow(memberId: String) {
        profileViewModel.unfollowArtist(memberId)
        profileViewModel.getFollowers(-1, 100)
    }
}