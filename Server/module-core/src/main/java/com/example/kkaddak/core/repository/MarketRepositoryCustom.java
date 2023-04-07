package com.example.kkaddak.core.repository;


import com.example.kkaddak.core.dto.MarketConditionReqDto;
import com.example.kkaddak.core.dto.MarketConditionResDto;
import com.example.kkaddak.core.exception.NoContentException;

import java.util.List;

public interface MarketRepositoryCustom {
    List<MarketConditionResDto> findMarketsByCondition(MarketConditionReqDto condition, int memberId) throws NoContentException;
    List<MarketConditionResDto> findMarketsByMyLike(MarketConditionReqDto condition, int memberId) throws NoContentException;
}
