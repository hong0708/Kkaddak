package com.example.kkaddak.api.service;

import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.core.dto.MarketConditionReqDto;
import com.example.kkaddak.core.dto.MarketReqDto;
import com.example.kkaddak.core.entity.Member;
import com.example.kkaddak.core.exception.NoContentException;
import org.springframework.stereotype.Service;

@Service
public interface MarketService {
    DataResDto<?> createMarket(MarketReqDto marketReqDto, Member member);
    DataResDto<?> getMarketAllByCondition(MarketConditionReqDto condition, Member member) throws NoContentException;
    DataResDto<?> getMarketAllByMyLike(MarketConditionReqDto condition, Member member) throws NoContentException;
    DataResDto<?> likeMarket(Member liker, int marketId);
    DataResDto<?> unlikeMarket(Member liker, int marketId);
    DataResDto<?> getMarketDetail(Member member, int marketId) throws Exception;
}
