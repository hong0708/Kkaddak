package com.example.kkaddak.api.contract;

import com.example.kkaddak.api.service.impl.MusicNFTContractWrapper;
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
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import static com.example.kkaddak.api.service.impl.MusicNFTContractWrapper.*;

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

//    @Test
    public void buyNFT() throws Exception {
        BigInteger gasPrice = BigInteger.valueOf(0L); // 20 Gwei
        BigInteger gasLimit = BigInteger.valueOf(4_300_000); // 가스 한도
        ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);

        System.out.println(credentials.getAddress());
        // ERC20_sol_KATToken 객체 생성
        MusicNFTContractWrapper musicNFT = new MusicNFTContractWrapper(contractAddress, web3j, credentials, contractGasProvider);

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
        MusicNFTContractWrapper musicNFT = new MusicNFTContractWrapper(contractAddress, web3j, credentials, contractGasProvider);

        //String EOA = "0xf10ccb49335c686147bdba507482bb3d3e3af1c4";
        RemoteFunctionCall<MusicNFTContractWrapper.SaleInfo> remoteFunctionCall = musicNFT.getTokenSaleInfo(new BigInteger("1"));

        try {
            MusicNFTContractWrapper.SaleInfo result = remoteFunctionCall.send();
            System.out.println("result :" + result.isSelling);
            System.out.println("result :" + result.price);
        } catch (IOException | TransactionException e) {
            System.err.println("Error while fetching the balance: " + e.getMessage());
        }
    }

    @Test
    void Test() throws Exception {
        ContractGasProvider contractGasProvider = createContractGasProvider();

        ReadonlyTransactionManager transactionManager = new ReadonlyTransactionManager(web3j, contractAddress);
        MusicNFTContractWrapper musicNFT = new MusicNFTContractWrapper(contractAddress, web3j, credentials, contractGasProvider);
        RemoteFunctionCall<MusicNFTData> NftDataFunctionCall = musicNFT.getMusicNFTData(new BigInteger("1"));
        RemoteFunctionCall<SaleInfo> saleInfoFunctionCall = musicNFT.getTokenSaleInfo(new BigInteger("1"));
        RemoteFunctionCall<List> saleHistoryFunctionCall = musicNFT.getSaleHistory(new BigInteger("1"));
        try {
            MusicNFTData nftData = NftDataFunctionCall.send();
            SaleInfo saleInfo = saleInfoFunctionCall.send();
            List<SaleHistory> saleHistory = (List<SaleHistory>) saleHistoryFunctionCall.send();

            System.out.println(nftData.getClass());
            System.out.println(saleInfo.getClass());
            System.out.println(saleHistory.getClass());
            System.out.println(nftData.createdDate);
            System.out.println(saleInfo.getClass());
            System.out.println(saleHistory.getClass());
        } catch (IOException | TransactionException e) {
            System.err.println("Error while fetching the balance: " + e.getMessage());
        }
    }

    private ContractGasProvider createContractGasProvider(){
        BigInteger gasPrice = BigInteger.valueOf(0L); // 20 Gwei
        BigInteger gasLimit = BigInteger.valueOf(4_300_000); // 가스 한도
        return new StaticGasProvider(gasPrice, gasLimit);
    }
}
