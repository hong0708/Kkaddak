package com.ssafy.kkaddak.presentation.join

import androidx.lifecycle.lifecycleScope
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.FragmentCompleteJoinBinding
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompleteJoinFragment :
    BaseFragment<FragmentCompleteJoinBinding>(R.layout.fragment_complete_join) {
    override fun initView() {
        viewLifecycleOwner.lifecycleScope.launch {
            delay(1000)
            navigate(CompleteJoinFragmentDirections.actionCompleteJoinFragmentToMainActivity())
        }
    }
}