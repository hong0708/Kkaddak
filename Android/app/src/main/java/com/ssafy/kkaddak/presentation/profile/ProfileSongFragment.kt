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
import com.ssafy.kkaddak.common.util.showToastMessage
import com.ssafy.kkaddak.databinding.FragmentProfileSongBinding
import com.ssafy.kkaddak.domain.entity.song.SongItem
import com.ssafy.kkaddak.presentation.MainActivity
import com.ssafy.kkaddak.presentation.base.BaseFragment
import com.ssafy.kkaddak.presentation.market.GridSpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
    private var checkBio = false
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

//    private var executor: Executor? = null
//    private var biometricPrompt: BiometricPrompt? = null
//    private var promptInfo: BiometricPrompt.PromptInfo? = null

    override fun initView() {
        setProfileSong()

//        biometricPrompt = setBiometricPrompt()
//        promptInfo = setPromptInfo()
    }

    override fun onResume() {
        super.onResume()
        profileViewModel.profileSongData.observe(viewLifecycleOwner) { response ->
            response?.let {
                profileSongAdapter.setSong(it)
            }
        }

        profileViewModel.getProfileSong(args.nickname)
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            profileViewModel.getProfileSong(args.nickname)
            delay(10000)
            profileViewModel.getProfileSong(args.nickname)
        }
    }

    override fun mintNFT(songItem: SongItem, bitmap: Bitmap) {
        // 다이얼로그 에서 이미지 생성 또는 여기서 생성하고 다이얼로그 띄우기
        profileViewModel.nftImageUrl.observe(viewLifecycleOwner) {
            if (it != "") {
                profileViewModel.checkBeforeNFT("")
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

                CoroutineScope(Dispatchers.Main).launch {
                    profileViewModel.changeSongState("COMPLETE", songUUID = songItem.songId)
                    delay(1000)
                    profileViewModel.getProfileSong(args.nickname)
                }
            }
        }
        saveImageToGallery(bitmap, songItem.songId)
    }

    override fun onConfirmButtonClicked(songId: String) {
        profileViewModel.deleteMySong(songId)
        profileViewModel.getProfileSong(ApplicationClass.preferences.nickname!!)
        showToast("음악이 삭제되었습니다.")
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
        /*if (checkBio) {
            CreateNFTDialog(requireActivity(), songData, this).show()
        } else {
            authenticateToEncrypt()
        }*/

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
            requireActivity().showToastMessage("그림 저장을 실패하였습니다")
        }
        requireActivity().showToastMessage("그림이 갤러리에 저장되었습니다")

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

    /* private val loginLauncher =
         registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
             Log.d(TAG, "registerForActivityResult - result : $result")
             if (result.resultCode == Activity.RESULT_OK) {
                 Log.d(TAG, "registerForActivityResult - RESULT_OK")
                 authenticateToEncrypt()  //생체 인증 가능 여부확인 다시 호출
             } else {
                 Log.d(TAG, "registerForActivityResult - NOT RESULT_OK")
             }
         }

     private fun setPromptInfo(): BiometricPrompt.PromptInfo {

         val promptBuilder: BiometricPrompt.PromptInfo.Builder = BiometricPrompt.PromptInfo.Builder()

         promptBuilder.setTitle("Biometric login for KKADDAK")
         promptBuilder.setSubtitle("Log in using your biometric credential")
         promptBuilder.setNegativeButtonText("Use account password")

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
             //  안면인식 ap사용 android 11부터 지원
             promptBuilder.setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
         }

         promptInfo = promptBuilder.build()
         return promptInfo as BiometricPrompt.PromptInfo
     }

     private fun setBiometricPrompt(): BiometricPrompt {
         executor = ContextCompat.getMainExecutor(requireContext())

         biometricPrompt = BiometricPrompt(
             requireActivity(),
             executor!!,
             object : BiometricPrompt.AuthenticationCallback() {

                 override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                     super.onAuthenticationError(errorCode, errString)
 //                    Toast.makeText(
 //                        requireActivity(),
 //                        """"지문 인식 ERROR [ errorCode: $errorCode, errString: $errString ]""".trimIndent(),
 //                        Toast.LENGTH_SHORT
 //                    ).show()
                     showToast("보안 인식에 실패하였습니다.")
                 }

                 override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                     super.onAuthenticationSucceeded(result)
                     showToast("성공! 이제 NFT를 발급 받으세요")

                     checkBio = true
                 }

                 override fun onAuthenticationFailed() {
                     super.onAuthenticationFailed()
                     showToast("보안 인식에 실패하였습니다.")
                 }

             })
         return biometricPrompt as BiometricPrompt
     }

     *//*
    * 생체 인식 인증을 사용할 수 있는지 확인
    * *//*
    fun authenticateToEncrypt() {
        var allow = false
        val biometricManager = BiometricManager.from(requireActivity())
        // when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)) {

            //생체 인증 가능
            BiometricManager.BIOMETRIC_SUCCESS -> allow = true

            //기기에서 생체 인증을 지원하지 않는 경우
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> allow = false

            //현재 생체 인증을 사용할 수 없는 경우
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> allow = false

            //생체 인식 정보가 등록되어 있지 않은 경우
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                allow = false

                val dialogBuilder = AlertDialog.Builder(requireActivity())
                dialogBuilder
                    .setTitle("나의앱")
                    .setMessage("지문 등록이 필요합니다. 지문등록 설정화면으로 이동하시겠습니까?")
                    .setPositiveButton("확인") { dialog, which -> goBiometricSettings() }
                    .setNegativeButton("취소") { dialog, which -> dialog.cancel() }
                dialogBuilder.show()
            }

            //기타 실패
            else -> allow = false

        }
        if (allow) {
            //인증 실행하기
            goAuthenticate()
        } else {
            showToast("보안 검증을 실행할 수 없습니다")
        }
    }

    *//*
    * 생체 인식 인증 실행
    * *//*
    private fun goAuthenticate() {
        Log.d(TAG, "goAuthenticate - promptInfo : $promptInfo")
        promptInfo?.let {
            biometricPrompt?.authenticate(it);  //인증 실행
        }
    }

    *//*
    * 지문 등록 화면으로 이동
    * *//*
    fun goBiometricSettings() {
        val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
            putExtra(
                Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )
        }
        loginLauncher.launch(enrollIntent)
    }

    companion object {
        const val TAG: String = "BiometricActivity"
    }*/
}