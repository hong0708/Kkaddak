package com.ssafy.kkaddak.presentation.market

import android.text.Editable
import android.text.TextWatcher
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.ssafy.kkaddak.ApplicationClass
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.common.util.BindingAdapters.setNormalImg
import com.ssafy.kkaddak.common.util.NFTFunction
import com.ssafy.kkaddak.common.util.WalletFunction
import com.ssafy.kkaddak.databinding.ActivityMainBinding
import com.ssafy.kkaddak.databinding.FragmentBuyBinding
import com.ssafy.kkaddak.presentation.MainActivity
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BuyFragment : BaseFragment<FragmentBuyBinding>(R.layout.fragment_buy) {

    private val args by navArgs<BuyFragmentArgs>()
    private lateinit var navController: NavController
    private val marketViewModel by activityViewModels<MarketViewModel>()
    var state = false

//    private var executor: Executor? = null
//    private var biometricPrompt: BiometricPrompt? = null
//    private var promptInfo: BiometricPrompt.PromptInfo? = null

    override fun initView() {
        (activity as MainActivity).hideBottomNavigation(true)
        initListener()
        getData()
        getBalance()

//        biometricPrompt = setBiometricPrompt()
//        promptInfo = setPromptInfo()
    }

    override fun navigateToProfile(creatorId: String) {}

    private fun initListener() {
        binding.apply {
            tvPayment.setOnClickListener {
                if (state) {
                    try {
                        marketViewModel.closeMarket(args.marketId)
                        showToast("NFT 구매가 완료되었습니다.")
                        NFTFunction().buyMusicNFT(
                            args.nftId.toBigInteger(),
                            args.nftPrice.toBigInteger()
                        )
                        navigate(BuyFragmentDirections.actionBuyFragmentToMarketFragment())

                        // authenticateToEncrypt()

                    } catch (e: Exception) {
                        e.printStackTrace()
                        showToast("NFT 구매에 실패하였습니다.")
                    }
                } else {
                    goWallet()
                }
            }
            tvWalletBalance.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(p0: Editable?) {
                    if (tvWalletBalance.text.toString()
                            .toDouble() >= args.nftPrice.toDouble() / 100000000
                    ) {
                        state = true
                        tvPayment.setText(R.string.content_buy_payment)
                    } else {
                        state = false
                        tvPayment.setText(R.string.content_buy_charge)
                    }
                }
            })
        }
    }

    private fun getData() {
        marketViewModel.getBuyData(args)
        binding.apply {
            ivNftImage.setNormalImg(args.nftImagePath.toUri())
            tvCreatorNickname.text = args.nftCreator
            tvReceiverAddress.text = args.sellerAccount
            tvNftKat.text = String.format("%.1f", args.nftPrice.toDouble() / 100000000)

            setWidth()
        }
    }

    private fun setWidth() {
        binding.apply {
            val titleWidth = tvTitleCreator.measuredWidth * 0.8
            val nicknameWidth = tvCreatorNickname.measuredWidth
            if (nicknameWidth >= titleWidth) {
                var params = tvCreatorNickname.layoutParams as ConstraintLayout.LayoutParams
                params.startToEnd = ConstraintLayout.LayoutParams.UNSET
                params.topToTop = ConstraintLayout.LayoutParams.UNSET
                params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                params.topToBottom = tvTitleCreator.id
                tvCreatorNickname.layoutParams = params

                params = tvContentBuyNft.layoutParams as ConstraintLayout.LayoutParams
                params.topToBottom = tvCreatorNickname.id
            }
        }
    }

    private fun getBalance() {
        if (ApplicationClass.preferences.walletAddress.toString() == "") {
            state = false
            showToast("지갑 등록 또는 생성을 진행해주세요.")
            binding.tvPayment.setText(R.string.content_buy_charge)
        } else {
            WalletFunction().balanceOf(binding.tvWalletBalance)
        }
    }

    fun goWallet() {
        // 다시 NFT 목록 화면으로 초기화
        popBackStack()
        popBackStack()

        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController

        ActivityMainBinding.inflate(layoutInflater).bottomNavigation.apply {
            setupWithNavController(navController)
            itemIconTintList = null
            selectedItemId = R.id.walletFragment
        }
    }

/*
    private val loginLauncher =
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
                    showToast("지문 인식 성공")

                    marketViewModel.closeMarket(args.marketId)
                    showToast("NFT 구매가 완료되었습니다.")
                    NFTFunction().buyMusicNFT(
                        args.nftId.toBigInteger(),
                        args.nftPrice.toBigInteger()
                    )
                    navigate(BuyFragmentDirections.actionBuyFragmentToMarketFragment())

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