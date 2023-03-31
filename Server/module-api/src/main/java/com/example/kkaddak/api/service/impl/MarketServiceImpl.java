package com.example.kkaddak.api.service.impl;


import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.api.dto.market.MarketDetailDto;
import com.example.kkaddak.api.dto.market.MarketResDto;
import com.example.kkaddak.api.exception.NotFoundException;
import com.example.kkaddak.api.service.MarketService;
import com.example.kkaddak.api.service.MusicNFTContract;
import com.example.kkaddak.core.dto.MarketConditionReqDto;
import com.example.kkaddak.core.dto.MarketConditionResDto;
import com.example.kkaddak.core.dto.MarketReqDto;
import com.example.kkaddak.core.entity.LikeMarket;
import com.example.kkaddak.core.entity.Market;
import com.example.kkaddak.core.entity.Member;
import com.example.kkaddak.core.exception.NoContentException;
import com.example.kkaddak.core.repository.LikeMarketRepository;
import com.example.kkaddak.core.repository.MarketRepository;
import com.example.kkaddak.core.utils.ErrorMessageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigInteger;
import java.util.List;

import static com.example.kkaddak.api.service.impl.MusicNFTContractWrapper.SaleHistory;
import static com.example.kkaddak.api.service.impl.MusicNFTContractWrapper.SaleInfo;

@Service
public class MarketServiceImpl implements MarketService {

    private final MarketRepository marketRepo;
    private final LikeMarketRepository likeMarketRepository;
    private final MusicNFTContract nftContract;

    @Autowired
    public MarketServiceImpl(
            MarketRepository marketRepo,
            LikeMarketRepository likeMarketRepository,
            @Qualifier("ReadOnlyMusicNFTContractWrapper") MusicNFTContractWrapper nftContract) {
        this.marketRepo = marketRepo;
        this.likeMarketRepository = likeMarketRepository;
        this.nftContract = nftContract;
    }

    @Override
    public DataResDto<?> createMarket(MarketReqDto marketReqDto, Member seller) {
        Market market = Market.builder().seller(seller).market(marketReqDto).build();
        Market savedMarket = marketRepo.save(market);
        MarketResDto marketResDto = MarketResDto.builder().market(savedMarket).build();
        return DataResDto.builder().statusMessage("마켓에 성공적으로 생성되었습니다.")
                .data(marketResDto)
                .build();
    }

    @Override
    public DataResDto<?> getMarketAllByCondition(MarketConditionReqDto condition, Member member) throws NoContentException {
        List<MarketConditionResDto> res = marketRepo.findMarketsByCondition(condition, member.getId());
        if (ObjectUtils.isEmpty(res))
            throw new NoContentException("조회된 마켓 목록이 없습니다.");
        return DataResDto.builder()
                .statusMessage("조건에 따라 마켓 목록이 조회되었습니다.")
                .data(res)
                .build();
    }

    @Override
    public DataResDto<?> getMarketAllByMyLike(MarketConditionReqDto condition, Member member) throws NoContentException {
        List<MarketConditionResDto> res = marketRepo.findMarketsByMyLike(condition, member.getId());
        if (ObjectUtils.isEmpty(res))
            throw new NoContentException("조회된 마켓 목록이 없습니다.");
        return DataResDto.builder()
                .statusMessage("조건에 따라 마켓 목록이 조회되었습니다.")
                .data(res)
                .build();
    }

    @Override
    public DataResDto<?> likeMarket(Member liker, int marketId) {
        Market market = marketRepo.findById(marketId)
                .orElseThrow(() -> new NotFoundException(ErrorMessageEnum.MARKET_NOT_EXIST.getMessage()));
        LikeMarket likeMarket = LikeMarket.builder().liker(liker).market(market).build();
        likeMarketRepository.save(likeMarket);
        return DataResDto.builder().statusMessage("해당 마켓 아이템이 북마크되었습니다.").data(true).build();
    }

    @Override
    public DataResDto<?> unlikeMarket(Member liker, int marketId) {
        Market market = marketRepo.findById(marketId)
                .orElseThrow(() -> new NotFoundException(ErrorMessageEnum.MARKET_NOT_EXIST.getMessage()));
        LikeMarket likeMarket = likeMarketRepository.findByLikerAndMarket(liker, market)
                .orElseThrow(() -> new NotFoundException(ErrorMessageEnum.LIKEMARKET_NOT_EXIST.getMessage()));

        likeMarketRepository.delete(likeMarket);
        return DataResDto.builder().statusCode(204).statusMessage("해당 마켓 아이템의 북마크가 취소되었습니다.").data(true).build();
    }

    public DataResDto<?> getMarketDetail(Member member, int marketId) throws Exception {
        Market market = marketRepo.findById(marketId)
                .orElseThrow(() -> new NotFoundException(ErrorMessageEnum.MARKET_NOT_EXIST.getMessage()));
        Member seller = market.getSeller();

        SaleInfo tokenSaleInfo = nftContract.getTokenSaleInfo(new BigInteger(market.getNftId())).send();
        List<SaleHistory> saleHistory = nftContract.getSaleHistory(new BigInteger(market.getNftId())).send();

        return DataResDto.builder()
                .statusMessage("판매 아이템 상세 정보입니다.")
                .data(MarketDetailDto.builder()
                        .market(market)
                        .saleInfo(tokenSaleInfo)
                        .histories(saleHistory)
                        .isLike(likeMarketRepository.existsByLikerAndMarket(member, market))
                        .sellerNickname(seller.getNickname())
                        .sellerAccount(seller.getAccount())
                        .build()
                ).build();
    }
}