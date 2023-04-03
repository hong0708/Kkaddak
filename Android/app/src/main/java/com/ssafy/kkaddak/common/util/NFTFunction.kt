package com.ssafy.kkaddak.common.util

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ssafy.kkaddak.ApplicationClass
import com.ssafy.kkaddak.common.util.NFT_sol_MusicNFT.*
import com.ssafy.kkaddak.domain.entity.profile.ProfileNFTDetailItem
import com.ssafy.kkaddak.domain.entity.profile.ProfileNFTItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.RemoteFunctionCall
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ReadonlyTransactionManager
import org.web3j.tx.gas.StaticGasProvider
import java.math.BigInteger

private const val INFURA_URL = "https://rpc.ssafy-blockchain.com"
private const val NFT_CONTRACT_ADDRESS = "0x0C0391FF59A532a51cf8E10F3C0632401EA0b4B8"
private const val TAG = "NFT info"

class NFTFunction {
    // Ethereum 네트워크에 연결
    private val web3j = Web3j.build(HttpService(INFURA_URL))

    // 가스 제공자 설정
    private val gasPrice = BigInteger.valueOf(0) // 20 Gwei
    private val gasLimit = BigInteger.valueOf(4_300_000) // 가스 한도
    private val contractGasProvider = StaticGasProvider(gasPrice, gasLimit)

    // 트랜잭션 매니저 조회용
    private val transactionManager = ReadonlyTransactionManager(web3j, NFT_CONTRACT_ADDRESS)

    fun getNFTCount() {
        Thread {
            val katToken = load(
                NFT_CONTRACT_ADDRESS,
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

    fun getTokensOfOwner(account: String?): MutableLiveData<List<ProfileNFTItem>> {
        val result = MutableLiveData<List<ProfileNFTItem>>()

        CoroutineScope(Dispatchers.IO).launch {

            val katToken = load(
                NFT_CONTRACT_ADDRESS,
                web3j,
                transactionManager,
                contractGasProvider
            )

            try {
                if (account != null) {
                    val remoteFunctionCall =
                        katToken.getTokensOfOwner(account) as RemoteFunctionCall<List<*>>

                    val nftList = remoteFunctionCall.send() as List<MusicNFTMetaData>
                    val list = mutableListOf<ProfileNFTItem>()

                    for (i in nftList) {
                        Log.d(TAG, "getTokensOfOwner: ${i}")
                        list.add(
                            ProfileNFTItem(
                                i.nftImageUrl,
                                i.tokenId
                            )
                        )
                    }
                    result.postValue(list)
                }
            } catch (e: Exception) {
                System.err.println("Error while get RecentTransactionList: ${e.message}")
            }
        }
        return result
    }

    fun getMetaData(nftId: BigInteger): MutableLiveData<ProfileNFTDetailItem> {

        val result = MutableLiveData<ProfileNFTDetailItem>()

        CoroutineScope(Dispatchers.IO).launch {
            val katToken = load(
                NFT_CONTRACT_ADDRESS,
                web3j,
                transactionManager,
                contractGasProvider
            )

            try {
                val remoteFunctionCall = katToken.getMusicNFTData(nftId)
                val remoteFunctionCallResult = remoteFunctionCall.send() as MusicNFTData

                val nftDetail = ProfileNFTDetailItem(
                    remoteFunctionCallResult.nftImageUrl,
                    remoteFunctionCallResult.coverImageUrl,
                    remoteFunctionCallResult.creatorNickname,
                    remoteFunctionCallResult.createdDate,
                    remoteFunctionCallResult.trackTitle,
                    remoteFunctionCallResult.combination
                )
                result.postValue(nftDetail)
            } catch (e: Exception) {
                System.err.println("Error while get RecentTransactionList: ${e.message}")
            }
        }
        return result
    }

    fun mintMusicNFT() {
        val credentials =
            Credentials.create(
                String(
                    ApplicationClass.keyStore.decryptData(
                        WalletFunction().decode(ApplicationClass.preferences.privateKey.toString())
                    )
                )
            )

        val katToken = load(
            NFT_CONTRACT_ADDRESS,
            web3j,
            credentials,
            contractGasProvider
        )
        CoroutineScope(Dispatchers.IO).launch {
            try {
//                val remoteFunctionCall = katToken.mintMusicNFT(
//
//                )
//
//                val registerSong = remoteFunctionCall.send()

            } catch (e: Exception) {
                System.err.println("Error while get RecentTransactionList: ${e.message}")
            }
        }
    }
}