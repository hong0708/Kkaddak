package com.example.kkaddak.api.service.impl;


import com.example.kkaddak.api.exception.NoContentException;
import com.example.kkaddak.api.exception.PagingQueryException;
import com.example.kkaddak.core.dto.AuctionReqDto;
import com.example.kkaddak.api.dto.auction.AuctionResDto;
import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.api.service.AuctionService;
import com.example.kkaddak.core.entity.Auction;
import com.example.kkaddak.core.entity.Member;
import com.example.kkaddak.core.repository.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        return DataResDto.builder().statusCode(200).statusMessage("옥션이 성공적으로 생성되었습니다.")
                .data(auctionResDto)
                .build();
    }

    @Override
    public DataResDto<?> getAuctionAllByPaging(Long limit, Long lastId) throws Exception  {
        Query nativeQuery;
        if (Objects.equals(lastId, -1L)){
            String query = "SELECT * FROM (select id from coredb.auction order by id desc limit :limit ) a1 Join coredb.auction a2 on a1.id = a2.id";
            nativeQuery = entityManager.createNativeQuery(query, Auction.class);
            nativeQuery.setParameter("limit", limit);
        }
        else{
            String query = "SELECT * FROM (select id from coredb.auction where id < :lastId order by id desc limit :limit ) a1 Join coredb.auction a2 on a1.id = a2.id";
            nativeQuery = entityManager.createNativeQuery(query, Auction.class);
            nativeQuery.setParameter("lastId", lastId);
            nativeQuery.setParameter("limit", limit);
        }
        List resultList;
        try{
            resultList = nativeQuery.getResultList();
        }
        catch(Exception e){
            throw new PagingQueryException(e.getMessage());
        }
        if (Objects.equals(resultList.size(), 0)) throw new NoContentException("조회된 경매 목록이 없습니다.");


        List<AuctionResDto> PagingResponse = (List<AuctionResDto>) resultList.stream().map(auction -> AuctionResDto.builder().auction((Auction) auction).build()).collect(Collectors.toList());
        return DataResDto.builder().statusCode(200).statusMessage("경매 목록 페이징 조회되었습니니다.")
                .data(PagingResponse).build();
    }
}
