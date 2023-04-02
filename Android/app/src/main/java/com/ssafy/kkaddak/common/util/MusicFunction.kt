package com.ssafy.kkaddak.common.util

import com.ssafy.kkaddak.ApplicationClass
import com.ssafy.kkaddak.common.util.Song_sol_MusicCopyRight.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.gas.StaticGasProvider
import java.math.BigInteger

private const val INFURA_URL = "https://rpc.ssafy-blockchain.com"
private const val MUSIC_CONTRACT_ADDRESS = "0x4d99223346658b1746a382045f0B68BCEFb3a2d3"
private const val TAG = "music info"

class MusicFunction {
    // Ethereum 네트워크에 연결
    private val web3j = Web3j.build(HttpService(INFURA_URL))

    // 가스 제공자 설정
    private val gasPrice = BigInteger.valueOf(0) // 20 Gwei
    private val gasLimit = BigInteger.valueOf(4_300_000) // 가스 한도
    private val contractGasProvider = StaticGasProvider(gasPrice, gasLimit)

    fun registerSong(){
        val credentials =
            Credentials.create(
                String(
                    ApplicationClass.keyStore.decryptData(
                        WalletFunction().decode(ApplicationClass.preferences.privateKey.toString())
                    )
                )
            )

        val katToken = load(
            MUSIC_CONTRACT_ADDRESS,
            web3j,
            credentials,
            contractGasProvider
        )
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val remoteFunctionCall = katToken.registerSong()

                val registerSong = remoteFunctionCall.send()

            } catch (e: Exception) {
                System.err.println("Error while get RecentTransactionList: ${e.message}")
            }
        }
    }
}