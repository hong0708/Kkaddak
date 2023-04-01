package com.ssafy.kkaddak.common.util

import com.ssafy.kkaddak.ApplicationClass
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ReadonlyTransactionManager
import org.web3j.tx.gas.StaticGasProvider
import java.math.BigInteger

private const val INFURA_URL = "https://rpc.ssafy-blockchain.com"
private const val NFT_CONTRACT_ADDRESS = "0x4d99223346658b1746a382045f0B68BCEFb3a2d3"
private const val TAG = "music info"

class MusicFunction {
    // Ethereum 네트워크에 연결
    private val web3j = Web3j.build(HttpService(INFURA_URL))

    // 가스 제공자 설정
    private val gasPrice = BigInteger.valueOf(0) // 20 Gwei
    private val gasLimit = BigInteger.valueOf(4_300_000) // 가스 한도
    private val contractGasProvider = StaticGasProvider(gasPrice, gasLimit)

    // 트랜잭션 매니저 조회용
    private val transactionManager = ReadonlyTransactionManager(web3j, NFT_CONTRACT_ADDRESS)

    fun registerSong(){
        val credentials =
            Credentials.create(
                String(
                    ApplicationClass.keyStore.decryptData(
                        decode(ApplicationClass.preferences.privateKey.toString())
                    )
                )
            )

        val katToken = KATToken_sol_KATToken.load(
            NFT_CONTRACT_ADDRESS,
            web3j,
            credentials,
            contractGasProvider
        )
    }
}