package com.example.kkaddak.api.contract;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.io.IOException;
import java.math.BigInteger;

@SpringBootTest
@Transactional
@ActiveProfiles("apitest")
public class NFTTransactionTest {

    @Autowired
    Web3j web3j;

    @Autowired
    Credentials credentials;

    @Value("${ethereum.contract.nft-address}")
    private String contractAddress;

    @Test
    public void buyNFT() throws Exception {
        BigInteger gasPrice = BigInteger.valueOf(0L); // 20 Gwei
        BigInteger gasLimit = BigInteger.valueOf(4_300_000); // 가스 한도
        ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);

        System.out.println(credentials.getAddress());
        // ERC20_sol_KATToken 객체 생성
        MusicNFT_sol_MusicNFT musicNFT = new MusicNFT_sol_MusicNFT(contractAddress, web3j, credentials, contractGasProvider);

        //String EOA = "0xf10ccb49335c686147bdba507482bb3d3e3af1c4";
        RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = musicNFT.buyMusicNFT(new BigInteger("1"), new BigInteger("2"));
        try {
            TransactionReceipt result = remoteFunctionCall.send();
            System.out.println("result :" + result);
        } catch (Exception e) {
            System.err.println("Error while fetching the balance: " + e.getMessage());
        }
    }

    @Test
    public void getNFT() throws Exception {
        BigInteger gasPrice = BigInteger.valueOf(0L); // 20 Gwei
        BigInteger gasLimit = BigInteger.valueOf(4_300_000); // 가스 한도
        ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);

        System.out.println(credentials.getAddress());
        // ERC20_sol_KATToken 객체 생성
        NFT_sol_MusicNFT musicNFT = new NFT_sol_MusicNFT(contractAddress, web3j, credentials, contractGasProvider);

        //String EOA = "0xf10ccb49335c686147bdba507482bb3d3e3af1c4";
        RemoteFunctionCall<Tuple2<Boolean, BigInteger>> remoteFunctionCall = musicNFT.NFTSaleInfo(new BigInteger("1"));

        try {
            Tuple2<Boolean, BigInteger> result = remoteFunctionCall.send();
            System.out.println("result :" + result);
        } catch (IOException | TransactionException e) {
            System.err.println("Error while fetching the balance: " + e.getMessage());
        }
    }
}
