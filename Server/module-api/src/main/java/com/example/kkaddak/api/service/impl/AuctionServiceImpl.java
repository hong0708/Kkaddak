package com.example.kkaddak.api.service.impl;


import com.example.kkaddak.api.dto.member.FollowResDto;
import com.example.kkaddak.api.exception.NoContentException;
import com.example.kkaddak.api.exception.NotFoundException;
import com.example.kkaddak.api.exception.PagingQueryException;
import com.example.kkaddak.core.dto.AuctionConditionReqDto;
import com.example.kkaddak.core.dto.AuctionConditionResDto;
import com.example.kkaddak.core.dto.AuctionReqDto;
import com.example.kkaddak.api.dto.auction.AuctionResDto;
import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.api.service.AuctionService;
import com.example.kkaddak.core.entity.Auction;
import com.example.kkaddak.core.entity.Follow;
import com.example.kkaddak.core.entity.LikeAuction;
import com.example.kkaddak.core.entity.Member;
import com.example.kkaddak.core.repository.AuctionRepository;
import com.example.kkaddak.core.repository.LikeAuctionRepository;
import com.example.kkaddak.core.utils.ErrorMessageEnum;
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
    private final LikeAuctionRepository likeAuctionRepository;

    @Override
    public DataResDto<?> createAuction(AuctionReqDto auctionReqDto, Member seller) {
        Auction auction = Auction.builder().seller(seller).auction(auctionReqDto).build();
        Auction savedAuction = auctionRepo.save(auction);
        AuctionResDto auctionResDto = AuctionResDto.builder().auction(savedAuction).build();
        return DataResDto.builder().statusMessage("옥션이 성공적으로 생성되었습니다.")
                .data(auctionResDto)
                .build();
    }

    @Override
    public DataResDto<?> getAuctionAllByCondition(AuctionConditionReqDto condition, Member member) throws NoContentException {
        List<AuctionConditionResDto> res = auctionRepo.findAuctionsByCondition(condition, member.getId());
        if (ObjectUtils.isEmpty(res))
            throw new NoContentException("조회된 경매 목록이 없습니다.");
        return DataResDto.builder()
                .statusMessage("조건에 따라 경매 목록이 조회되었습니다.")
                .data(res)
                .build();
    }
    @Override
    public DataResDto<?> getAuctionAllByMyLike(AuctionConditionReqDto condition, Member member) throws NoContentException {
        List<AuctionConditionResDto> res = auctionRepo.findAuctionsByMyLike(condition, member.getId());
        if (ObjectUtils.isEmpty(res))
            throw new NoContentException("조회된 경매 목록이 없습니다.");
        return DataResDto.builder()
                .statusMessage("조건에 따라 경매 목록이 조회되었습니다.")
                .data(res)
                .build();
    }

    @Override
    public DataResDto<?> likeAuction(Member liker, int auctionId) {
        Auction auction = auctionRepo.findById(auctionId)
                .orElseThrow(() -> new NotFoundException(ErrorMessageEnum.AUCTION_NOT_EXIST.getMessage()));
        LikeAuction likeAuction = LikeAuction.builder().liker(liker).auction(auction).build();
        likeAuctionRepository.save(likeAuction);
        return DataResDto.builder().statusMessage("해당 경매가 북마크되었습니다.").data(true).build();
    }

    @Override
    public DataResDto<?> unlikeAuction(Member liker, int auctionId) {
        Auction auction = auctionRepo.findById(auctionId)
                .orElseThrow(() -> new NotFoundException(ErrorMessageEnum.AUCTION_NOT_EXIST.getMessage()));
        LikeAuction likeAuction = likeAuctionRepository.findByLikerAndAuction(liker, auction)
                .orElseThrow(() -> new NotFoundException(ErrorMessageEnum.LIKEAUCTION_NOT_EXIST.getMessage()));

        likeAuctionRepository.delete(likeAuction);
        return DataResDto.builder().statusCode(204).statusMessage("해당 경매의 북마크가 취소되었습니다.").data(true).build();
    }
}
