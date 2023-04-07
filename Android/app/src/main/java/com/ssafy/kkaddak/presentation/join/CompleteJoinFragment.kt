package com.ssafy.kkaddak.presentation.join

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.common.util.fadeOutView
import com.ssafy.kkaddak.databinding.FragmentCompleteJoinBinding
import com.ssafy.kkaddak.presentation.MainActivity
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompleteJoinFragment :
    BaseFragment<FragmentCompleteJoinBinding>(R.layout.fragment_complete_join) {
    override fun initView() {
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            fadeOutView(binding.clWelcomeKkaddak, requireContext())
            delay(1000)
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            delay(1000)
            (activity as InitActivity).goToMain()
        }
    }

    override fun navigateToProfile(creatorId: String) {}
}