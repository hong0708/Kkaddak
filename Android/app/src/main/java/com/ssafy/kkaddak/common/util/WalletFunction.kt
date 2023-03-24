package com.ssafy.kkaddak.common.util

import android.widget.TextView
import com.ssafy.kkaddak.ApplicationClass
import org.bouncycastle.crypto.digests.KeccakDigest
import org.bouncycastle.util.encoders.Hex
import org.web3j.crypto.Credentials
import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.Keys
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ReadonlyTransactionManager
import org.web3j.tx.gas.StaticGasProvider
import java.math.BigInteger

private const val INFURA_URL = "https://rpc.ssafy-blockchain.com"
private const val CONTRACT_ADDRESS = "0x987A75dB8A085B0147F022Cb2D0EeaDbEFadC712"
private var MY_WALLET_ADDRESS = ""
private const val TAG = "wallet info"

class WalletFunction {

    // Ethereum 네트워크에 연결
    private val web3j = Web3j.build(HttpService(INFURA_URL))

    // 가스 제공자 설정
    private val gasPrice = BigInteger.valueOf(20_000_000_000L) // 20 Gwei
    private val gasLimit = BigInteger.valueOf(4_300_000) // 가스 한도
    private val contractGasProvider = StaticGasProvider(gasPrice, gasLimit)

    // 트랜잭션 매니저 조회용
    private val transactionManager = ReadonlyTransactionManager(web3j, CONTRACT_ADDRESS)

    fun generateWallet() {
        Thread {
            // ECDSA 키 쌍 생성
            val ecKeyPair: ECKeyPair = Keys.createEcKeyPair()
            // 개인 키 추출
            val privateKey: String = ecKeyPair.privateKey.toString(16)
            // 공개 키 추출
            val publicKey: String = ecKeyPair.publicKey.toString(16)
            // Credentials 객체 생성
            val credentials: Credentials = Credentials.create(privateKey)

            val address = generateAccountAddress(ecKeyPair.publicKey.toByteArray())

            ApplicationClass.preferences.walletAddress = address
            ApplicationClass.preferences.privateKey = privateKey

            /*val token = ERC20_sol_KATToken(
                CONTRACT_ADDRESS,
                web3j,
                transactionManager,
                contractGasProvider
            )*/

            // ERC20_sol_KATToken 객체 생성
            val katToken =
                ERC20_sol_KATToken(CONTRACT_ADDRESS, web3j, credentials, contractGasProvider)

            val EOA = "0xf10ccb49335c686147bdba507482bb3d3e3af1c4"
            val remoteFunctionCall = katToken.balanceOf(EOA)

            try {
                val balance = remoteFunctionCall.send()
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
                //transactionManager,
                Credentials.create(privateKey),
                contractGasProvider
            )

            ApplicationClass.preferences.walletAddress = walletAddress
            ApplicationClass.preferences.privateKey = privateKey


            val EOA = "0xf10ccb49335c686147bdba507482bb3d3e3af1c4"
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

    fun balanceOf(walletAddress: String, textView: TextView) {

        Thread {
            val katToken = ERC20_sol_KATToken.load(
                CONTRACT_ADDRESS,
                web3j,
                transactionManager,
                //Credentials.create(privateKey),
                contractGasProvider
            )

            //val EOA = "0xf10ccb49335c686147bdba507482bb3d3e3af1c4"
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

    private fun generateAccountAddress(publicKey: ByteArray): String {
        // Add 04 prefix to indicate that it's uncompressed public key.
        val uncompressedPublicKey = byteArrayOf(0x04) + publicKey
        // Keccak-256 hash function is used to hash the public key.
        val keccak256Digest = KeccakDigest(256)
        val hashedPublicKey = ByteArray(keccak256Digest.digestSize)
        keccak256Digest.update(uncompressedPublicKey, 0, uncompressedPublicKey.size)
        keccak256Digest.doFinal(hashedPublicKey, 0)
        // Only the last 20 bytes are used as the account address.
        val address = hashedPublicKey.copyOfRange(hashedPublicKey.size - 20, hashedPublicKey.size)
        // Convert byte array to hexadecimal string.
        val addressHex = Hex.toHexString(address)
        // Add "0x" prefix to indicate that it's a hexadecimal string.
        return "0x$addressHex"
    }
}