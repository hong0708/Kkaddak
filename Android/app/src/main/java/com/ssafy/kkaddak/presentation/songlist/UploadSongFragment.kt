package com.ssafy.kkaddak.presentation.songlist

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
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
                songViewModel.setSongFile(
                    it.data as Uri,
                    File(absolutelySongPath(it.data, requireContext()))
                )
            }
        }
    }

    override fun initView() {
        (activity as MainActivity).HideBottomNavigation(true)
        initListener()
        setData()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).HideBottomNavigation(false)
    }

    private fun setData() {
        songViewModel.songFile.observe(viewLifecycleOwner) {
            binding.ivCameraIcon.visibility = View.GONE
            binding.ivMusicCoverPicture.setNormalImg(it)
        }
    }

    @SuppressLint("DiscouragedApi")
    private fun initListener() {
        binding.clMusicCover.setOnClickListener {
            setAlbumView()
        }
        binding.tvUploadMusic.setOnClickListener {
            setSongFileView()
        }
        binding.rgGenre.setOnCheckedChangeListener { _, checkedId ->
            Log.d("asdf", "initListener: ${checkedId}")
            val index =
                resources.getIdentifier("rb_${checkedId}", "id", requireContext().packageName)
            val selectedButton = requireView().findViewById(index) as TextView
            songViewModel.genre = selectedButton.text.toString()
            selectedButton.setBackgroundResource(R.drawable.bg_rect_indigo_to_navy_blue_radius40)
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
}