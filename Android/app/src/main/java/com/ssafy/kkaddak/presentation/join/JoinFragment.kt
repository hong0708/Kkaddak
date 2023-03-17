package com.ssafy.kkaddak.presentation.join

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.ssafy.kkaddak.ApplicationClass
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.databinding.FragmentJoinBinding
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class JoinFragment : BaseFragment<FragmentJoinBinding>(R.layout.fragment_join) {

    private val joinViewModel by viewModels<JoinViewModel>()
    private val fromAlbumActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        result.data?.let {
            if (it.data != null) {
                joinViewModel.setProfileImg(
                    it.data as Uri,
                    File(absolutelyPath(it.data, requireContext()))
                )
            }
        }
    }

    override fun initView() {
        binding.viewModel = joinViewModel
        //joinViewModel.requestCurrentToken()

        initListener()
        observeState()
        setTextWatcher()

        binding.ivProfileImage.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (joinViewModel.cancelSignUp()) {
            ApplicationClass.preferences.clearPreferences()
        }
    }

    private fun initListener() {
        binding.apply {
            clProfileImage.setOnClickListener { setAlbumView() }
            etCheckNickName.addTextChangedListener { joinViewModel.setNickname(it.toString()) }
            btnBack.setOnClickListener { popBackStack() }

            clConfirm.setOnClickListener {
                if (joinViewModel.profileImgUri.value == null) {
                    joinViewModel.updateProfileWithoutImg()
                } else {
                    joinViewModel.updateProfile()
                }
            }
            tvCheckNickName.setOnClickListener {
                if (joinViewModel.nickname.value == null) {
                    //showToast("닉네임을 입력해주세요.")
                    Toast.makeText(context, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    joinViewModel.checkDuplication()
                }
            }
        }
    }

    private fun observeState() {
        joinViewModel.isDuplicate.observe(viewLifecycleOwner) {
            when (it) {
                true -> //showToast("중복된 닉네임입니다.")
                    Toast.makeText(context, "중복된 닉네임입니다.", Toast.LENGTH_SHORT).show()
                false -> //showToast("사용할 수 있는 닉네임입니다.")
                    Toast.makeText(context, "사용할 수 있는 닉네임입니다.", Toast.LENGTH_SHORT).show()
                else -> {}
            }
        }

        joinViewModel.isSuccess.observe(viewLifecycleOwner) {
            //Log.d(TAG, "observeState: ")
            when (it) {
                true -> navigate(JoinFragmentDirections.actionJoinFragmentToCompleteJoinFragment())
                else -> {}
            }
        }
    }

    private fun setTextWatcher() {
        binding.etCheckNickName.addTextChangedListener {
            joinViewModel.nickname.value = binding.etCheckNickName.text.toString()
        }
    }

    private fun setAlbumView() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
                fromAlbumActivityLauncher.launch(
                    Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                )
                binding.tvProfileCameraIcon.visibility = View.GONE
            }
            else -> {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_READ_STORAGE_PERMISSION
                )
            }
        }
    }

    private fun absolutelyPath(path: Uri?, context: Context): String {
        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        val index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()
        val result = c?.getString(index!!)
        c?.close()
        return result!!
    }

    companion object {
        const val REQUEST_READ_STORAGE_PERMISSION = 1
    }
}