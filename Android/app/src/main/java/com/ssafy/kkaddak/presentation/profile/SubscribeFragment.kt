package com.ssafy.kkaddak.presentation.profile

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.ssafy.kkaddak.ApplicationClass
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.common.util.BindingAdapters.setProfileImg
import com.ssafy.kkaddak.common.util.WalletFunction
import com.ssafy.kkaddak.databinding.FragmentSubscribeBinding
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubscribeFragment :
    BaseFragment<FragmentSubscribeBinding>(R.layout.fragment_subscribe) {

    private val args by navArgs<SubscribeFragmentArgs>()
    private val profileViewModel by activityViewModels<ProfileViewModel>()

//    private var executor: Executor? = null
//    private var biometricPrompt: BiometricPrompt? = null
//    private var promptInfo: BiometricPrompt.PromptInfo? = null

    override fun initView() {
        setData()
        initListener()

//        biometricPrompt = setBiometricPrompt()
//        promptInfo = setPromptInfo()
    }

    private fun initListener() {
        binding.apply {
            tvWalletBalance.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (tvWalletBalance.text.toString().toFloat() >= 5) {
                        tvPayment.apply {
                            visibility = View.VISIBLE
                            tvBalanceLack.visibility = View.GONE
                            setOnClickListener {
                                profileViewModel.followArtist(args.memberId)
                                // 구독 시 결제 진행
                                WalletFunction().transfer(
                                    args.address,
                                    (5 * 100000000).toLong(),
                                    "구독"
                                )
                                showToast("구독 후원이 완료되었습니다.")
                                popBackStack()
                                //authenticateToEncrypt()
                            }
                        }
                    } else {
                        binding.tvBalanceLack.visibility = View.VISIBLE
                        showToast("잔액이 부족합니다.")
                    }
                }
            })
        }
    }

    private fun setData() {
        binding.apply {
            tvCreatorNickname.text = args.nickname
            ivProfileImage.setProfileImg(args.profileImg)
            tvReceiverAddress.text = args.address

            if (ApplicationClass.preferences.walletAddress.toString() == "") {
                showToast("지갑 등록 또는 생성을 진행해주세요.")
            } else {
                WalletFunction().balanceOf(tvWalletBalance)
            }
        }
    }

    override fun navigateToProfile(creatorId: String) {
        navigate(SubscribeFragmentDirections.actionSubscribeFragmentToOtherProfileFragment(creatorId))
    }

/*    private val loginLauncher =
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

                    profileViewModel.followArtist(args.memberId)
                    // 구독 시 결제 진행
                    WalletFunction().transfer(
                        args.address,
                        (5 * 100000000).toLong(),
                        "구독"
                    )
                    popBackStack()
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
        const val TAG: String = "SubscribeFragment"
    }*/
}