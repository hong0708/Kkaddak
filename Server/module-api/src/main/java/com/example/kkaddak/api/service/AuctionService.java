package com.example.kkaddak.api.service;

import com.example.kkaddak.core.dto.AuctionReqDto;
import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.core.entity.Member;
import org.springframework.stereotype.Service;

@Service
public interface AuctionService {
    DataResDto<?> createAuction(AuctionReqDto auctionReqDto, Member member);
}
