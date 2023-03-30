
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
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.io.IOException;
import java.math.BigInteger;

@SpringBootTest
@Transactional
@ActiveProfiles("apitest")
public class ContractTest {

    @Autowired
    private Web3j web3j;

    @Autowired
    private Credentials credentials;

    @Value("${ethereum.contract.wallet-address}")
    private String contractAddress;

    @Test
    public void getBalance() throws Exception {

        // 가스 제공자 설정
        BigInteger gasPrice = BigInteger.valueOf(0L); // 20 Gwei
        BigInteger gasLimit = BigInteger.valueOf(4_300_000); // 가스 한도
        ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);

        ReadonlyTransactionManager transactionManager = new ReadonlyTransactionManager(web3j, contractAddress);
        // ERC20_sol_KATToken 객체 생성
        ERC20_sol_KATToken katToken = new ERC20_sol_KATToken(contractAddress, web3j, transactionManager, contractGasProvider);

        String EOA = "0xba5155e3e46f474c947aee8ce77d4132d1ed93d0";
        RemoteFunctionCall<BigInteger> remoteFunctionCall = katToken.balanceOf(EOA);

        try {
            BigInteger balance = remoteFunctionCall.send();
            System.out.println("Balance of address " + EOA + ": " + balance);
        } catch (IOException | TransactionException e) {
            System.err.println("Error while fetching the balance: " + e.getMessage());
        }
    }

    @Test
    public void transfer() throws Exception {

        // 가스 제공자 설정
        BigInteger gasPrice = BigInteger.valueOf(0); // 20 Gwei
        BigInteger gasLimit = BigInteger.valueOf(3000000); // 가스 한도
        ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);

        // ERC20_sol_KATToken 객체 생성
        KATToken_sol_KATToken katToken = new KATToken_sol_KATToken(contractAddress, web3j, credentials, contractGasProvider);

        System.out.println(credentials.getAddress());
        String EOA = "0x8fdED880ED0E79c1209130c0e477A7eeE8956CE4";
        RemoteFunctionCall<TransactionReceipt> remoteFunctionCall = katToken.transfer("0x1e5ece0c6abecb6f0328734651c337ab3a524ddb","0x2c1d4e00a773f659084610f91f5decf963bf78e5", new BigInteger("1200000000"), "TRANSFER");
        try {
            TransactionReceipt balance = remoteFunctionCall.send();
            System.out.println("Balance of address " + EOA + " " + balance.getLogs());
        } catch (IOException | TransactionException e) {
            System.err.println("Error while fetching the balance: " + e.getMessage());
        }
    }

    @Test
    public void getTransferLog() throws Exception {
        BigInteger gasPrice = BigInteger.valueOf(0); // 20 Gwei
        BigInteger gasLimit = BigInteger.valueOf(3000000); // 가스 한도
        ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);

        KATToken_sol_KATToken katToken = new KATToken_sol_KATToken(contractAddress, web3j, credentials, contractGasProvider);
        katToken.getTransferLog("0x1E5eCE0C6abEcB6f0328734651C337ab3a524DDB").send();


    }
}