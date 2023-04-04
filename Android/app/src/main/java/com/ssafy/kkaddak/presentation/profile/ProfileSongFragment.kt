package com.ssafy.kkaddak.presentation.profile

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.kkaddak.ApplicationClass
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.common.util.MusicFunction
import com.ssafy.kkaddak.common.util.NFTFunction
import com.ssafy.kkaddak.common.util.WalletFunction
import com.ssafy.kkaddak.databinding.FragmentProfileSongBinding
import com.ssafy.kkaddak.domain.entity.song.SongItem
import com.ssafy.kkaddak.presentation.MainActivity
import com.ssafy.kkaddak.presentation.base.BaseFragment
import com.ssafy.kkaddak.presentation.market.GridSpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class ProfileSongFragment :
    BaseFragment<FragmentProfileSongBinding>(R.layout.fragment_profile_song),
    DeleteRejectedSongDialogListener,
    CreateNFTDialogInterface {

    private lateinit var nftFile: File

    private val args by navArgs<ProfileSongFragmentArgs>()
    private val profileViewModel by activityViewModels<ProfileViewModel>()
    private val profileSongAdapter by lazy {
        ProfileSongAdapter(
            args.isMine,
            this::getSongDetail,
            this::deleteMySong,
            this::uploadMySong
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
        profileViewModel.getProfileSong(args.nickname)
    }

    override fun mintNFT(songItem: SongItem, bitmap: Bitmap) {
        // 다이얼로그 에서 이미지 생성 또는 여기서 생성하고 다이얼로그 띄우기
        profileViewModel.nftImageUrl.observe(viewLifecycleOwner) {
            if (it != "") {
                NFTFunction().mintMusicNFT(
                    String(
                        ApplicationClass.keyStore.decryptData(
                            WalletFunction().decode(ApplicationClass.preferences.walletAddress.toString())
                        )
                    ),
                    songItem.coverPath,
                    songItem.nickname!!,
                    songItem.songTitle,
                    it!!,
                    songItem.combination!!.joinToString(separator = "")
                )

                MusicFunction().registerSong(
                    songItem.songTitle,
                    songItem.nickname,
                    songItem.coverPath,
                    songItem.songPath!!,
                    System.currentTimeMillis().toBigInteger(),
                    songItem.combination.fold("") { acc, i -> acc + i }.toBigInteger()
                )
            }
        }
        saveImageToGallery(bitmap, songItem.songId)
    }

    override fun onConfirmButtonClicked(songId: String) {
        profileViewModel.deleteMySong(songId)
        profileViewModel.getProfileSong(ApplicationClass.preferences.nickname!!)
        Toast.makeText(requireContext(), "음악이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
        profileViewModel.getProfileSong(ApplicationClass.preferences.nickname!!)
    }

    override fun navigateToProfile(creatorId: String) {}

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
        profileViewModel.getProfileSong(args.nickname)
    }

    private fun getSongDetail(songId: String) {
        (activity as MainActivity).apply {
            setSongDetail(songId)
        }
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

    private fun uploadMySong(songData: SongItem) {
        CreateNFTDialog(requireActivity(), songData, this).show()
    }

    private fun saveImageToGallery(bitmap: Bitmap, id: String) {
        // 권한 체크
        if (!checkPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ||
            !checkPermission(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ) {
            requestPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermission(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
            return
        }

        // 그림 저장
        if (!imageExternalSave(
                requireActivity(),
                bitmap,
                requireActivity().getString(R.string.app_name)
            )
        ) {
            Toast.makeText(activity, "그림 저장을 실패하였습니다", Toast.LENGTH_SHORT).show()
        }
        Toast.makeText(activity, "그림이 갤러리에 저장되었습니다", Toast.LENGTH_SHORT).show()

        val file = File(nftFile.absolutePath)
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val nftMultipart = MultipartBody.Part.createFormData("nftImage", file.name, requestFile)
        profileViewModel.uploadNFTImage(id, nftMultipart)
    }

    private fun imageExternalSave(
        context: Context,
        bitmap: Bitmap,
        path: String
    ): Boolean {

        val state = Environment.getExternalStorageState()

        if (Environment.MEDIA_MOUNTED == state) {
            val rootPath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .toString()
            val dirName = "/" + path
            val fileName = System.currentTimeMillis().toString() + ".png"
            val savePath = File(rootPath + dirName)
            savePath.mkdirs()

            val file = File(savePath, fileName)
            if (file.exists()) file.delete()

            try {
                val out = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                out.flush()
                out.close()
                nftFile = file
                // 갤러리 갱신
                context.sendBroadcast(
                    Intent(
                        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                        Uri.parse("file://" + Environment.getExternalStorageDirectory())
                    )
                )

                return true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return false
    }

    private fun checkPermission(activity: Activity, permission: String): Boolean {
        val permissionChecker =
            ContextCompat.checkSelfPermission(activity.applicationContext, permission)
        // 권한이 없으면 권한 요청
        if (permissionChecker == PackageManager.PERMISSION_GRANTED) return true
        requestPermission(activity, permission)
        return false
    }

    private fun requestPermission(activity: Activity, permission: String) {
        ActivityCompat.requestPermissions(activity, arrayOf(permission), 1)
    }
}