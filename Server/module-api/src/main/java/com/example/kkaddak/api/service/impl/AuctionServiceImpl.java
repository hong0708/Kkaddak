package com.example.kkaddak.api.service.impl;


import com.example.kkaddak.api.exception.NoContentException;
import com.example.kkaddak.api.exception.PagingQueryException;
import com.example.kkaddak.core.dto.AuctionConditionReqDto;
import com.example.kkaddak.core.dto.AuctionConditionResDto;
import com.example.kkaddak.core.dto.AuctionReqDto;
import com.example.kkaddak.api.dto.auction.AuctionResDto;
import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.api.service.AuctionService;
import com.example.kkaddak.core.entity.Auction;
import com.example.kkaddak.core.entity.Member;
import com.example.kkaddak.core.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {

    private final AuctionRepository auctionRepo;
    private final EntityManager entityManager;

    @Override
    public DataResDto<?> createAuction(AuctionReqDto auctionReqDto, Member seller) {
        Auction auction = Auction.builder().seller(seller).auction(auctionReqDto).build();
        Auction savedAuction = auctionRepo.save(auction);
        AuctionResDto auctionResDto = AuctionResDto.builder().auction(savedAuction).build();
        return DataResDto.builder().statusMessage("옥션이 성공적으로 생성되었습니다.")
                .data(auctionResDto)
                .build();
    }

    public DataResDto<?> getAuctionAllByCondition(AuctionConditionReqDto condition, Member member) throws NoContentException {
        List<AuctionConditionResDto> res = auctionRepo.findAuctionsByCondition(condition, member.getId());
        if (ObjectUtils.isEmpty(res))
            throw new NoContentException("조회된 경매 목록이 없습니다.");
        return DataResDto.builder()
                .statusMessage("조건에 따라 경매 목록이 조회되었습니다.")
                .data(res)
                .build();
    }
}
