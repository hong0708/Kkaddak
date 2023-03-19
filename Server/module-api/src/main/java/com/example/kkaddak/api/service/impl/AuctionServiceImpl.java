package com.example.kkaddak.api.service.impl;


import com.example.kkaddak.core.dto.AuctionReqDto;
import com.example.kkaddak.api.dto.AuctionResDto;
import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.api.service.AuctionService;
import com.example.kkaddak.core.entity.Auction;
import com.example.kkaddak.core.entity.Member;
import com.example.kkaddak.core.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {

    private final AuctionRepository auctionRepo;

    @Override
    public DataResDto<?> createAuction(AuctionReqDto auctionReqDto, Member seller) {
        Auction auction = Auction.builder().seller(seller).auction(auctionReqDto).build();
        Auction savedAuction = auctionRepo.save(auction);
        AuctionResDto auctionResDto = AuctionResDto.builder().auction(savedAuction).build();
        return DataResDto.builder().statusCode(200).statusMessage("옥션이 성공적으로 생성되었습니다.")
                .data(auctionResDto)
                .build();
    }
}
