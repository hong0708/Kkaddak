package com.example.kkaddak.api.service;

import com.example.kkaddak.api.service.impl.MusicNFTContractWrapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.web3j.protocol.core.RemoteFunctionCall;

import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.List;

@Component
public interface MusicNFTContract {
    RemoteFunctionCall<MusicNFTData> getMusicNFTData(BigInteger tokenId);
    RemoteFunctionCall<List> getSaleHistory(BigInteger tokenId);
    RemoteFunctionCall<SaleInfo> getTokenSaleInfo(BigInteger tokenId);
}
