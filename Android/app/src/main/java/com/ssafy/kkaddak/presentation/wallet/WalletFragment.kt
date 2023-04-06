package com.ssafy.kkaddak.presentation.wallet

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.kkaddak.ApplicationClass
import com.ssafy.kkaddak.R
import com.ssafy.kkaddak.common.util.WalletFunction
import com.ssafy.kkaddak.databinding.FragmentWalletBinding
import com.ssafy.kkaddak.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.co.bootpay.android.Bootpay
import kr.co.bootpay.android.events.BootpayEventListener
import kr.co.bootpay.android.models.BootExtra
import kr.co.bootpay.android.models.BootItem
import kr.co.bootpay.android.models.Payload
import org.json.JSONObject

@AndroidEntryPoint
class WalletFragment : BaseFragment<FragmentWalletBinding>(R.layout.fragment_wallet),
    SetWalletDialogInterface {

    private val walletViewModel by viewModels<WalletViewModel>()
    private val recentTransactionListAdapter by lazy { RecentTransactionListAdapter() }
//    private var executor: Executor? = null
//    private var biometricPrompt: BiometricPrompt? = null
//    private var promptInfo: BiometricPrompt.PromptInfo? = null

    override fun initView() {
        initListener()
        getBalance()
        initRecyclerView()

//        biometricPrompt = setBiometricPrompt()
//        promptInfo = setPromptInfo()
    }

    override fun setWallet(walletAddress: String, privateKey: String) {
        WalletFunction().insertUserWallet(walletAddress, privateKey, binding.tvTotalBalance)
        visibility(true)
        walletViewModel.registerWalletAccount(walletAddress)
        CoroutineScope(Dispatchers.Main).launch {
            delay(500)
            initRecyclerView()
        }
    }

    override fun generateWallet() {
        WalletFunction().generateWallet(binding.tvTotalBalance)
        visibility(true)
        walletViewModel.registerWalletAccount(
            String(
                ApplicationClass.keyStore.decryptData(
                    WalletFunction().decode(ApplicationClass.preferences.walletAddress.toString())
                )
            )
        )
        initRecyclerView()
    }

    override fun chargeCoin(amount: Double) {
        val items: MutableList<BootItem> = ArrayList()
        items.add(BootItem().setName("결제권").setId("1234").setQty(1).setPrice(amount))
        val payload = Payload()

        payload.setApplicationId(getString(R.string.APPLICATION_ID))
            .setOrderName("까딱까딱 구독권 결제")
            .setPg("kcp")
            .setOrderId(
                String(
                    ApplicationClass.keyStore.decryptData(
                        WalletFunction().decode(ApplicationClass.preferences.walletAddress.toString())
                    )
                )
            )
            .setPrice(amount)
            .setExtra(BootExtra()).items = items

        Bootpay.init(requireActivity().supportFragmentManager, requireActivity().applicationContext)
            .setPayload(payload)
            .setEventListener(object : BootpayEventListener {
                override fun onCancel(data: String) {
                    Log.d("bootpay", "cancel: $data")
                }

                override fun onError(data: String) {
                    Log.d("bootpay", "error: $data")
                }

                override fun onClose() {
                    Bootpay.removePaymentWindow()
                }

                override fun onIssued(data: String) {
                    Log.d("bootpay", "issued: $data")
                }

                override fun onConfirm(data: String): Boolean {
                    Log.d("bootpay", "confirm: $data")
                    return true
                }

                override fun onDone(data: String) {
                    val jsonObject = JSONObject(data)
                    sendReceipt(jsonObject.getJSONObject("data").getString("receipt_id"))
                }
            }).requestPayment()
    }

    private fun sendReceipt(receiptId: String) {
        walletViewModel.chargeCoin(receiptId)
        showToast("충전되었습니다. \n금액 반영은 1분정도 소요됩니다.")
    }

    private fun initRecyclerView() {
        binding.rvRecentTransactionList.apply {
            adapter = recentTransactionListAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }

        if (ApplicationClass.preferences.walletAddress.toString() != "") {
            WalletFunction().getRecentTransactionList().observe(viewLifecycleOwner) { lists ->
                recentTransactionListAdapter.setData(lists)
            }
        }
    }

    private fun initListener() {
        binding.apply {
            ivOpenWalletDialog.setOnClickListener {
                SetWalletDialog(requireActivity(), this@WalletFragment).show()
            }

            clWalletInfo.setOnClickListener {
                WalletInfoDialog(requireActivity()).show()
            }

            clCharge.setOnClickListener {
                if (ApplicationClass.preferences.walletAddress.toString() == "") {
                    showToast("지갑 등록 또는 생성을 진행해주세요.")
                } else {
                    // 충전 플로우
                    ChargeCoinDialog(requireActivity(), this@WalletFragment).show()

                    // authenticateToEncrypt()
                }
            }
        }
    }

    private fun getBalance() {
        if (ApplicationClass.preferences.walletAddress.toString() == "") {
            visibility(false)
        } else {
            visibility(true)
            WalletFunction().balanceOf(binding.tvTotalBalance)
        }
    }

    private fun visibility(wallet: Boolean) {
        binding.apply {
            if (wallet) {
                tvTotalBalance.visibility = View.VISIBLE
                tvNoWallet.visibility = View.GONE
            } else {
                tvTotalBalance.visibility = View.GONE
                tvNoWallet.visibility = View.VISIBLE
            }
        }
    }

    override fun navigateToProfile(creatorId: String) {
        navigate(WalletFragmentDirections.actionWalletFragmentToOtherProfileFragment(creatorId))
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
                    showToast("지문 인식 성공")
                    ChargeCoinDialog(requireActivity(), this@WalletFragment).show()
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