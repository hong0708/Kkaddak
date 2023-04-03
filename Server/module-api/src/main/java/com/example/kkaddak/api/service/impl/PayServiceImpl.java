package com.example.kkaddak.api.service.impl;

import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.api.dto.pay.ExchangeReqDto;
import com.example.kkaddak.api.exception.ServiceException;
import com.example.kkaddak.api.exception.UnauthorizationException;
import com.example.kkaddak.api.service.KATTokenContract;
import com.example.kkaddak.api.service.PayService;
import com.example.kkaddak.core.entity.Member;
import com.example.kkaddak.core.utils.ErrorMessageEnum;
import kr.co.bootpay.Bootpay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Objects;


@Service
public class PayServiceImpl implements PayService {
    @Value("${ethereum.admin-address}")
    private String adminAddress;

    @Value("${ethereum.exchange-rate}")
    private Integer exchageRate;

    private final KATTokenContract katTokenContract;
    private final Bootpay bootpay;

    @Autowired
    public PayServiceImpl(
            @Qualifier("KATTokenContractWrapper") KATTokenContractWrapper katTokenContract,
            Bootpay bootpay) {
        this.katTokenContract = katTokenContract;
        this.bootpay = bootpay;
    }

    @Override
public DataResDto<?> exchangeCashToCoin(Member member, ExchangeReqDto exchangeReqDto) throws Exception {
        // 부트 페이 접근 권한 요청
        try {
            HashMap token = bootpay.getAccessToken();
            if (!Objects.isNull(token.get("error_code"))) {
                throw new ServiceException(ErrorMessageEnum.UNAUTHORIZED_BOOTPAY.getMessage());
            }
        }
        catch(Exception e){
            throw new ServiceException(ErrorMessageEnum.UNAUTHORIZED_BOOTPAY.getMessage());
        }
        // 결제 정보 조회
        HashMap result = bootpay.getReceipt(exchangeReqDto.getReceiptId());
        if (!Objects.isNull(result.get("error_code"))) {
            // 결제 과정에서 error 발생
            throw new IllegalArgumentException(ErrorMessageEnum.ABNORMAL_PAYMENT.getMessage());
        }
        // 결제 완료 상태 이외 receipt 요청 예외 처리
        if (!Objects.equals(result.get("status"), 1.0)) {
            // 결제 과정에서 error 발생
            throw new IllegalArgumentException(ErrorMessageEnum.EXPIRED_RECEIPT.getMessage());
        }
        if (Objects.isNull(result.get("order_id"))){
            // 결제 payload(account address) 누락
            throw new IllegalArgumentException(ErrorMessageEnum.ACCOUNT_ADDRESS_REQUIRED.getMessage());
        }
        String accountAddress = (String) result.get("order_id");
        if (!Objects.equals(accountAddress, member.getAccount())) {
            // 결제자와 요청자 account address 일치 여부 확인
            throw new UnauthorizationException(ErrorMessageEnum.UNAUTHORIZED_EXCHANGE_REQUEST.getMessage());
        }
        // KAT 송금 요청
        try {
            katTokenContract
                    .transfer(
                            adminAddress,
                            accountAddress,
                            new BigInteger(String.valueOf(Math.round((Double) result.get("price") * exchageRate))),
                            "충전")
                    .send();
            return DataResDto.builder()
                    .statusMessage("코인 구매가 완료되었습니다.")
                    .data(true)
                    .build();
        }
        catch (Exception e){
            throw new ServiceException(e.getMessage());
        }
    }
}
