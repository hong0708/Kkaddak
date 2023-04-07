package com.ssafy.kkaddak.presentation.profile

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.ssafy.kkaddak.ApplicationClass
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.common.util.BindingAdapters.setProfileImg
import com.ssafy.kkaddak.databinding.FragmentEditProfileBinding
import com.ssafy.kkaddak.presentation.MainActivity
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class EditProfileFragment :
    BaseFragment<FragmentEditProfileBinding>(R.layout.fragment_edit_profile),
    FileDeleteDialogListener {

    private val profileViewModel by activityViewModels<ProfileViewModel>()

    private val fromAlbumActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        result.data?.let {
            if (it.data != null) {
                profileViewModel.setProfileImg(
                    it.data as Uri,
                    File(absolutelyPath(it.data, requireContext()))
                )

                Glide.with(requireContext())
                    .load(it.data as Uri)
                    .placeholder(R.drawable.ic_profile_image)
                    .error(R.drawable.ic_profile_image)
                    .circleCrop()
                    .into(binding.ivProfileImage)
            }
        }
    }

    override fun initView() {
        (activity as MainActivity).hideBottomNavigation(true)
        observeState()
        initListener()
        setData()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).hideBottomNavigation(false)
    }

    private fun setData() {
        binding.etCheckNickName.setText(ApplicationClass.preferences.nickname)
        ApplicationClass.preferences.profileImg?.let {
            binding.ivProfileImage.setProfileImg(it)
            binding.ivProfileCameraIcon.visibility = View.GONE
        }
    }

    private fun showDuplicateInfo(async: Int) {
        if (profileViewModel.isDuplicate.value == true) {
            showToast("중복된 닉네임입니다.")
        } else {
            showToast("사용할 수 있는 닉네임입니다.")
        }
    }

    private fun initListener() {
        binding.apply {
            clProfileImage.setOnClickListener { setAlbumView() }

            ivBack.setOnClickListener { popBackStack() }

            clConfirm.setOnClickListener {
                ApplicationClass.preferences.nickname = etCheckNickName.text.toString()
                if (profileViewModel.profileImgUri.value != null) {
                    profileViewModel.updateProfile(etCheckNickName.text.toString())
                } else {
                    if (profileViewModel.profileImgStr.value == null) {
                        profileViewModel.updateProfileWithoutImg(etCheckNickName.text.toString())
                    } else {
                        profileViewModel.updateProfileWithExistingImg(etCheckNickName.text.toString())
                    }
                }
                showToast("프로필이 수정되었습니다.")
                popBackStack()
            }

            tvCheckNickName.setOnClickListener {
                if (etCheckNickName.text.isEmpty()) {
                    showToast("닉네임을 입력해주세요.")
                } else {
                    lifecycleScope.launch {
                        val async =
                            profileViewModel.checkDuplication(etCheckNickName.text.toString())
                        showDuplicateInfo(async)
                    }
                }
            }
        }
    }

    override fun onConfirmButtonClicked() {
        profileViewModel.profileImgStr.value = null
        profileViewModel.profileImgUri.value = null
        profileViewModel.profileImgMultiPart = null
    }

    override fun navigateToProfile(creatorId: String) {}

    private fun observeState() {
        profileViewModel.profileImgUri.observe(viewLifecycleOwner) {
            binding.ivProfileImage.setProfileImg(it)
        }
        profileViewModel.profileImgStr.observe(viewLifecycleOwner) {
            binding.ivProfileImage.setProfileImg(it)
        }
        profileViewModel.isDuplicate.observe(viewLifecycleOwner) {
            if (it == true) {
                binding.clConfirm.apply {
                    setBackgroundResource(R.drawable.bg_rect_night_rider_radius15)
                    isEnabled = false
                }
            } else {
                binding.clConfirm.apply {
                    setBackgroundResource(R.drawable.bg_rect_bitter_sweet_to_neon_pink_radius15)
                    isEnabled = true
                }
            }
        }

        profileViewModel.profileImgUri.observe(viewLifecycleOwner) {
            when (it) {
                null -> binding.ivProfileCameraIcon.visibility = View.VISIBLE
                else -> binding.ivProfileCameraIcon.visibility = View.GONE
            }
        }
    }

    private fun setAlbumView() {
        if (profileViewModel.profileImgUri.value == null && profileViewModel.profileImgStr.value == null) {
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
                }
                else -> {
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        REQUEST_READ_STORAGE_PERMISSION
                    )
                }
            }
        } else {
            val dialog = DeleteFileDialog(requireContext(), this)
            dialog.show()
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