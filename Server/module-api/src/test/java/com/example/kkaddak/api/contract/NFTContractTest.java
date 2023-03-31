package com.example.kkaddak.api.contract;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("apitest")
public class NFTContractTest {

    static final String NftContractAddress = "0x4c8d755a77c651b159bea33f020bcd438c688a9e2bcdd71dd57d84634e48cc97";

}
