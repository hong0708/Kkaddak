package com.example.kkaddak.api.service;

import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;

public interface KATTokenContract {
    RemoteFunctionCall<TransactionReceipt> transfer(String from, String recipient, BigInteger amount, String transferType);
}
