package com.ssafy.kkaddak.common.util

import android.util.Log
import com.ssafy.kkaddak.ApplicationClass
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ReadonlyTransactionManager
import org.web3j.tx.gas.StaticGasProvider
import java.math.BigInteger

private const val INFURA_URL = "https://rpc.ssafy-blockchain.com"
private const val CONTRACT_ADDRESS = "0xC6c12F436234F5A9E98C10c8562E28228aFf458A"
private const val TAG = "wallet info"

class NFTFunction {
    // Ethereum 네트워크에 연결
    private val web3j = Web3j.build(HttpService(INFURA_URL))

    // 가스 제공자 설정
    private val gasPrice = BigInteger.valueOf(0) // 20 Gwei
    private val gasLimit = BigInteger.valueOf(4_300_000) // 가스 한도
    private val contractGasProvider = StaticGasProvider(gasPrice, gasLimit)

    // 트랜잭션 매니저 조회용
    private val transactionManager = ReadonlyTransactionManager(web3j, CONTRACT_ADDRESS)

    fun getNFTCount(){
        Thread {
            val katToken = MusicNFT_sol_MusicNFT.load(
                CONTRACT_ADDRESS,
                web3j,
                transactionManager,
                contractGasProvider
            )

            val remoteFunctionCall = katToken.balanceOf(
                String(
                    ApplicationClass.keyStore.decryptData(
                        WalletFunction().decode(ApplicationClass.preferences.walletAddress.toString())
                    )
                )
            )

            try {
                val count = remoteFunctionCall.send().toString()
                Log.d(TAG, "getNFTList: $count")

            } catch (e: Exception) {
                System.err.println("Error while fetching the balance: ${e.message}")
            }
        }.start()
    }

    fun getTokensOfOwner(){
        Thread {
            val katToken = MusicNFT_sol_MusicNFT.load(
                CONTRACT_ADDRESS,
                web3j,
                transactionManager,
                contractGasProvider
            )

            val remoteFunctionCall = katToken.getTokensOfOwner(
                String(
                    ApplicationClass.keyStore.decryptData(
                        WalletFunction().decode(ApplicationClass.preferences.walletAddress.toString())
                    )
                )
            )

            try {
                val count = remoteFunctionCall.send().toString()
                Log.d(TAG, "getTokensOfOwner: $count")

            } catch (e: Exception) {
                System.err.println("Error while fetching the balance: ${e.message}")
            }
        }.start()
    }
}