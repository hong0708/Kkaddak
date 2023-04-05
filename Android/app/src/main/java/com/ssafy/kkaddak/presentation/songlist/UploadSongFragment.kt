package com.ssafy.kkaddak.presentation.songlist

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.ssafy.kkaddak.ApplicationClass
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.common.util.BindingAdapters.setNormalImg
import com.ssafy.kkaddak.databinding.FragmentUploadSongBinding
import com.ssafy.kkaddak.presentation.MainActivity
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class UploadSongFragment :
    BaseFragment<FragmentUploadSongBinding>(R.layout.fragment_upload_song) {

    private val songViewModel by activityViewModels<SongViewModel>()
    private val fromAlbumActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        result.data?.let {
            if (it.data != null) {
                songViewModel.setCoverFile(
                    it.data as Uri,
                    File(absolutelyCoverPath(it.data, requireContext()))
                )
            }
        }
    }
    private val fromMusicActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        result.data?.let {
            if (it.data != null) {
                val selectedFileUri = it.data as Uri
                val selectedFilePath = File(absolutelySongPath(selectedFileUri, requireContext()))
                binding.etSongName.setText(selectedFilePath.name.toString())
                songViewModel.setSongFile(
                    selectedFileUri,
                    selectedFilePath
                )
            }
        }
    }
    private val genreList: ArrayList<TextView> = arrayListOf()
    private val moodList: ArrayList<TextView> = arrayListOf()
    var selectedGenre = ""
    var selectedMood = arrayListOf<String>()

    override fun initView() {
        (activity as MainActivity).hideBottomNavigation(true)
        initGenre()
        initMood()
        initListener()
        setCoverImage()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).hideBottomNavigation(false)
        songViewModel.coverFile.value = null
    }

    private fun setCoverImage() {
        songViewModel.coverFile.observe(viewLifecycleOwner) {
            binding.ivMusicCover.setNormalImg(it)
        }
    }

    @SuppressLint("DiscouragedApi")
    private fun initGenre() {
        for (i in 1..7) {
            val filter =
                resources.getIdentifier("tv_genre_${i}", "id", requireContext().packageName)
            (requireView().findViewById(filter) as TextView).apply {
                genreList.add(this)
            }
        }
    }

    @SuppressLint("DiscouragedApi")
    private fun initMood() {
        for (i in 1..7) {
            val filter =
                resources.getIdentifier("tv_mood_${i}", "id", requireContext().packageName)
            (requireView().findViewById(filter) as TextView).apply {
                moodList.add(this)
            }
        }
    }

    @SuppressLint("DiscouragedApi")
    private fun initListener() {
        binding.apply {
            ivBack.setOnClickListener {
                popBackStack()
            }
            ivMusicCover.setOnClickListener {
                setAlbumView()
                songViewModel.coverFile.value = null
            }
            tvUploadMusic.setOnClickListener {
                setSongFileView()
            }
            clConfirmUpload.setOnClickListener {
                if (etMusicTitle.text == null || selectedGenre == "" || selectedMood.isEmpty()
                    || songViewModel.coverFile.value == null || songViewModel.songFile.value == null
                ) {
                    showToast("모든 정보를 입력해주세요")
                } else {
                    if (ApplicationClass.preferences.walletAddress.toString() == "") {
                        showToast("지갑 등록 또는 생성을 진행해주세요.")
                    } else {
                        songViewModel.uploadSong(
                            etMusicTitle.text.toString(),
                            selectedGenre,
                            selectedMood
                        )
                        popBackStack()
                        showToast("등록되었습니다")
                    }
                }
            }
        }
        initGenreListener()
        initMoodListener()
    }

    private fun initGenreListener() {
        for (i in 0..6) {
            genreList[i].let { genre ->
                genre.setOnClickListener {
                    for (j in 0..6) {
                        genreList[j].setBackgroundResource(R.drawable.bg_rect_stroke1_han_purple_radius40)
                    }
                    genre.setBackgroundResource(R.drawable.bg_rect_indigo_to_navy_blue_radius40)
                    selectedGenre = genre.text.toString()
                }
            }
        }
    }

    private fun initMoodListener() {
        for (i in 0..6) {
            moodList[i].let { filter ->
                filter.setOnClickListener {
                    if (filter.isSelected) {
                        filter.isSelected = false
                        filter.setBackgroundResource(R.drawable.bg_rect_stroke1_han_purple_radius40)
                        selectedMood.remove(moodList[i].text.toString())
                    } else {
                        filter.isSelected = true
                        filter.setBackgroundResource(R.drawable.bg_rect_indigo_to_navy_blue_radius40)
                        selectedMood.add(moodList[i].text.toString())
                    }
                }
            }
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

    private fun setSongFileView() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
                fromMusicActivityLauncher.launch(
                    Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
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
    }

    private fun absolutelyCoverPath(path: Uri?, context: Context): String {
        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        val index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()
        val result = c?.getString(index!!)
        c?.close()
        return result!!
    }

    private fun absolutelySongPath(path: Uri?, context: Context): String {
        val proj: Array<String> = arrayOf(MediaStore.Audio.Media.DATA)
        val c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        val index = c?.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
        c?.moveToFirst()
        val result = c?.getString(index!!)
        c?.close()
        return result!!
    }

    companion object {
        const val REQUEST_READ_STORAGE_PERMISSION = 1
    }

    override fun navigateToProfile(creatorId: String) {
        navigate(
            UploadSongFragmentDirections.actionUploadSongFragmentToOtherProfileFragment(
                creatorId
            )
        )
    }
}