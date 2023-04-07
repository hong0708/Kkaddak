package com.example.kkaddak.api.service;


import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.api.dto.pay.ExchangeReqDto;
import com.example.kkaddak.core.entity.Member;
import org.springframework.stereotype.Service;

@Service
public interface PayService {
    DataResDto<?> exchangeCashToCoin(Member member, ExchangeReqDto exchangeReqDto) throws Exception;
}
