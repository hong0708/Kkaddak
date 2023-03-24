package com.example.kkaddak.core.repository;


import com.example.kkaddak.core.dto.AuctionConditionReqDto;
import com.example.kkaddak.core.dto.AuctionConditionResDto;
import com.example.kkaddak.core.exception.NoContentException;

import java.util.List;

public interface AuctionRepositoryCustom {
    List<AuctionConditionResDto> findAuctionsByCondition(AuctionConditionReqDto condition, int memberId) throws NoContentException;
    List<AuctionConditionResDto> findAuctionsByMyLike(AuctionConditionReqDto condition, int memberId) throws NoContentException;
}
