package com.ssafy.kkaddak.common.util

import android.util.Base64
import android.util.Log
import android.widget.TextView
import com.ssafy.kkaddak.ApplicationClass
import org.web3j.crypto.Credentials
import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.Keys
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ReadonlyTransactionManager
import org.web3j.tx.gas.StaticGasProvider
import java.math.BigInteger

private const val INFURA_URL = "https://rpc.ssafy-blockchain.com"
private const val CONTRACT_ADDRESS = "0x0Acd0Cf85bB7C08f8782Fb1106C98312eE517818"
private const val TAG = "wallet info"

class WalletFunction {

    // Ethereum 네트워크에 연결
    private val web3j = Web3j.build(HttpService(INFURA_URL))

    // 가스 제공자 설정
    private val gasPrice = BigInteger.valueOf(0) // 20 Gwei
    private val gasLimit = BigInteger.valueOf(4_300_000) // 가스 한도
    private val contractGasProvider = StaticGasProvider(gasPrice, gasLimit)

    // 트랜잭션 매니저 조회용
    private val transactionManager = ReadonlyTransactionManager(web3j, CONTRACT_ADDRESS)

    fun generateWallet(textView: TextView) {
        Thread {

            // ECDSA 키 쌍 생성 난수생성기를 통해 생성한 안전한 키쌍
            val ecKeyPair: ECKeyPair = Keys.createEcKeyPair()
            // 개인 키 추출
            val privateKey: String = ecKeyPair.privateKey.toString(16)
            // 공개 키 추출
            val publicKey: String = ecKeyPair.publicKey.toString(16)
            // Credentials 객체 생성
            val credentials: Credentials = Credentials.create(privateKey)

            val address = Keys.getAddress(ecKeyPair.publicKey)
            /*val address = credentials.address*/

            ApplicationClass.preferences.walletAddress =
                encodeToString(ApplicationClass.keyStore.encryptData(address.toByteArray()))
            ApplicationClass.preferences.privateKey =
                encodeToString(ApplicationClass.keyStore.encryptData(privateKey.toByteArray()))

            // 지갑 생성 시 로그로 꼭 확인 및 지갑 정보에서 같은 값인지 확인
            Log.d(TAG, "generateWallet - Wallet Address: $address  privateKey : $privateKey")

            // ERC20_sol_KATToken 객체 생성
            val katToken =
                ERC20_sol_KATToken(CONTRACT_ADDRESS, web3j, credentials, contractGasProvider)

            val remoteFunctionCall = katToken.balanceOf(CONTRACT_ADDRESS)

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
            val katToken = ERC20_sol_KATToken.load(
                CONTRACT_ADDRESS,
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
            val katToken = ERC20_sol_KATToken.load(
                CONTRACT_ADDRESS,
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
            val katToken = ERC20_sol_KATToken.load(
                CONTRACT_ADDRESS,
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
                    "0x2c1d4e00a773f659084610f91f5decf963bf78e5",
                    remoteFunctionCall.send().toString().toBigInteger()
                ).send().toString()
                Log.d(TAG, "approve: $approve")

            } catch (e: Exception) {
                System.err.println("Error while fetching the balance: ${e.message}")
            }
        }.start()
    }

    fun transfer(targetAddress: String, amount: Long) {

        Thread {
            val credentials =
                Credentials.create(
                    String(
                        ApplicationClass.keyStore.decryptData(
                            decode(ApplicationClass.preferences.privateKey.toString())
                        )
                    )
                )

            val katToken = ERC20_sol_KATToken.load(
                CONTRACT_ADDRESS,
                web3j,
                credentials,
                contractGasProvider
            )

            try {
                val remoteFunctionCall = katToken.transfer(
                    targetAddress,
                    amount.toBigInteger()
                )

                val transfer = remoteFunctionCall.send().toString()
                Log.d(TAG, "transfer: $transfer")

            } catch (e: Exception) {
                System.err.println("Error while transfer the balance: ${e.message}")
            }
        }.start()
    }

    fun encodeToString(byteArray: ByteArray): String {
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun decode(string: String): ByteArray {
        return Base64.decode(string, Base64.DEFAULT)
    }
}