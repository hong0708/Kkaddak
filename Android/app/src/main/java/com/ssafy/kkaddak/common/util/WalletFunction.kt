package com.ssafy.kkaddak.common.util

import android.util.Base64
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.ssafy.kkaddak.ApplicationClass
import com.ssafy.kkaddak.common.util.KATToken_sol_KATToken.*
import org.web3j.crypto.Credentials
import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.Keys
import org.web3j.protocol.core.RemoteFunctionCall
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ReadonlyTransactionManager
import org.web3j.tx.gas.StaticGasProvider
import java.math.BigInteger
import com.ssafy.kkaddak.domain.entity.wallet.RecentTransactionItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val INFURA_URL = "https://rpc.ssafy-blockchain.com"
private const val KAT_CONTRACT_ADDRESS = "0xfB9843b34f1aB19d82Ba25DB6865897fA1311a74"
private const val TAG = "wallet info"

class WalletFunction {

    // Ethereum 네트워크에 연결
    private val web3j = Web3j.build(HttpService(INFURA_URL))

    // 가스 제공자 설정
    private val gasPrice = BigInteger.valueOf(0) // 20 Gwei
    private val gasLimit = BigInteger.valueOf(4_300_000) // 가스 한도
    private val contractGasProvider = StaticGasProvider(gasPrice, gasLimit)

    // 트랜잭션 매니저 조회용
    private val transactionManager = ReadonlyTransactionManager(web3j, KAT_CONTRACT_ADDRESS)

    fun generateWallet(textView: TextView) {

        // ECDSA 키 쌍 생성 난수생성기를 통해 생성한 안전한 키쌍
        val ecKeyPair: ECKeyPair = Keys.createEcKeyPair()
        // 개인 키 추출
        val privateKey: String = ecKeyPair.privateKey.toString(16)
        // 공개 키 추출
        val publicKey: String = ecKeyPair.publicKey.toString(16)
        // Credentials 객체 생성
        val credentials: Credentials = Credentials.create(privateKey)
        // wallet address
        val address = credentials.address

        ApplicationClass.preferences.walletAddress =
            encodeToString(ApplicationClass.keyStore.encryptData(address.toByteArray()))
        ApplicationClass.preferences.privateKey =
            encodeToString(ApplicationClass.keyStore.encryptData(privateKey.toByteArray()))

        Thread {
            // ERC20_sol_KATToken 객체 생성
            val katToken = load(KAT_CONTRACT_ADDRESS, web3j, credentials, contractGasProvider)

            val remoteFunctionCall = katToken.balanceOf(KAT_CONTRACT_ADDRESS)

            try {
                val balance = remoteFunctionCall.send().toString()
                val formattedBalance = (balance.toFloat() / 100000000).toString()

                textView.post {
                    textView.text = formattedBalance
                }
            } catch (e: Exception) {
                System.err.println("Error while fetching the balance: ${e.message}")
            }
        }.start()
    }

    fun insertUserWallet(walletAddress: String, privateKey: String, textView: TextView) {
        Thread {
            val katToken = load(
                KAT_CONTRACT_ADDRESS,
                web3j,
                Credentials.create(privateKey),
                contractGasProvider
            )

            ApplicationClass.preferences.walletAddress =
                encodeToString(ApplicationClass.keyStore.encryptData(walletAddress.toByteArray()))
            ApplicationClass.preferences.privateKey =
                encodeToString(ApplicationClass.keyStore.encryptData(privateKey.toByteArray()))

            val remoteFunctionCall = katToken.balanceOf(walletAddress)

            try {
                val balance = remoteFunctionCall.send().toString()
                val formattedBalance = (balance.toFloat() / 100000000).toString()

                textView.post {
                    textView.text = formattedBalance
                }
            } catch (e: Exception) {
                System.err.println("Error while fetching the balance: ${e.message}")
            }
        }.start()
    }

    fun balanceOf(textView: TextView) {
        val walletAddress =
            String(
                ApplicationClass.keyStore.decryptData(
                    decode(ApplicationClass.preferences.walletAddress.toString())
                )
            )
        Thread {
            val katToken = load(
                KAT_CONTRACT_ADDRESS,
                web3j,
                transactionManager,
                contractGasProvider
            )
            val remoteFunctionCall = katToken.balanceOf(walletAddress)

            try {
                val balance = remoteFunctionCall.send().toString()
                val formattedBalance = (balance.toFloat() / 100000000).toString()

                textView.post {
                    textView.text = formattedBalance
                }
            } catch (e: Exception) {
                System.err.println("Error while fetching the balance: ${e.message}")
            }
        }.start()
    }

    fun approve() {
        Thread {
            val credentials =
                Credentials.create(
                    String(
                        ApplicationClass.keyStore.decryptData(
                            decode(ApplicationClass.preferences.privateKey.toString())
                        )
                    )
                )
            val katToken = load(
                KAT_CONTRACT_ADDRESS,
                web3j,
                credentials,
                contractGasProvider
            )
            val remoteFunctionCall = katToken.balanceOf(
                String(
                    ApplicationClass.keyStore.decryptData(
                        decode(ApplicationClass.preferences.walletAddress.toString())
                    )
                )
            )

            try {
                val approve = katToken.approve(
                    String(
                        ApplicationClass.keyStore.decryptData(
                            decode(ApplicationClass.preferences.walletAddress.toString())
                        )
                    ),
                    remoteFunctionCall.send().toString().toBigInteger()
                ).send().toString()
                Log.d(TAG, "approve: $approve")

            } catch (e: Exception) {
                System.err.println("Error while fetching the balance: ${e.message}")
            }
        }.start()
    }

    fun transfer(targetAddress: String, amount: Long, transferType: String) {
        Thread {
            val credentials =
                Credentials.create(
                    String(
                        ApplicationClass.keyStore.decryptData(
                            decode(ApplicationClass.preferences.privateKey.toString())
                        )
                    )
                )

            val katToken = load(
                KAT_CONTRACT_ADDRESS,
                web3j,
                credentials,
                contractGasProvider
            )

            try {
                val remoteFunctionCall = katToken.transfer(
                    String(
                        ApplicationClass.keyStore.decryptData(
                            decode(ApplicationClass.preferences.walletAddress.toString())
                        )
                    ),
                    targetAddress,
                    amount.toBigInteger(),
                    transferType
                )

                val transfer = remoteFunctionCall.send().toString()
                Log.d(TAG, "transfer: $transfer")

            } catch (e: Exception) {
                System.err.println("Error while transfer the balance: ${e.message}")
            }
        }.start()
    }

    fun getRecentTransactionList(): MutableLiveData<List<RecentTransactionItem>> {

        val result = MutableLiveData<List<RecentTransactionItem>>()

        CoroutineScope(Dispatchers.IO).launch {
            val credentials = Credentials.create(
                String(
                    ApplicationClass.keyStore.decryptData(
                        decode(ApplicationClass.preferences.privateKey.toString())
                    )
                )
            )

            val katToken = load(
                KAT_CONTRACT_ADDRESS,
                web3j,
                transactionManager,
                contractGasProvider
            )

            try {
                val remoteFunctionCall = katToken.getTransferLog(
                    String(
                        ApplicationClass.keyStore.decryptData(
                            decode(ApplicationClass.preferences.walletAddress.toString())
                        )
                    )
                ) as RemoteFunctionCall<List<*>>

                val transferList = remoteFunctionCall.send() as List<TransferData>

                val recentTransactionList = mutableListOf<RecentTransactionItem>()
                for (i in transferList) {
                    recentTransactionList.add(
                        RecentTransactionItem(
                            i.sender,
                            i.recipient,
                            i.timeStamp,
                            i.amount,
                            i.transferType
                        )
                    )
                }
                result.postValue(recentTransactionList)
            } catch (e: Exception) {
                System.err.println("Error while get RecentTransactionList: ${e.message}")
            }
        }
        return result
    }

    private fun encodeToString(byteArray: ByteArray): String {
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun decode(string: String): ByteArray {
        return Base64.decode(string, Base64.DEFAULT)
    }
}