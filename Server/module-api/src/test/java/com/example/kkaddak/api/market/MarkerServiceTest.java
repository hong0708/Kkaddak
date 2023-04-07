package com.example.kkaddak.api.market;

import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.api.dto.market.MarketDetailDto;
import com.example.kkaddak.api.dto.market.MarketResDto;
import com.example.kkaddak.api.service.MarketService;
import com.example.kkaddak.core.dto.MarketReqDto;
import com.example.kkaddak.core.entity.LikeMarket;
import com.example.kkaddak.core.entity.Market;
import com.example.kkaddak.core.entity.Member;
import com.example.kkaddak.core.repository.LikeMarketRepository;
import com.example.kkaddak.core.repository.MarketRepository;
import com.example.kkaddak.core.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@ActiveProfiles("apitest")
public class MarkerServiceTest {

    @Autowired
    MarketService marketService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MarketRepository marketRepository;

    @Autowired
    LikeMarketRepository likeMarketRepository;

    @Test
    @DisplayName("마켓 생성 서비스 테스트")
    void CreateMarketTest(){
        Member member = Member.builder()
                .email("john@example.com")
                .memberType("회원")
                .nickname("MemberServiceTest1")
                .profilePath("profile/tom.jpg")
                .build();
        Member savedMember = memberRepository.save(member);

        MarketReqDto marketReqDto = MarketReqDto.builder()
                .creatorName("songCreator")
                .songTitle("songTitle")
                .nftId("nftIdFromContract")
                .nftImagePath("imagePath")
                .price(0.1)
                .build();

        DataResDto resDto = marketService.createMarket(marketReqDto, savedMember);

        assertEquals(resDto.getStatusCode(), 200);
        assertEquals(resDto.getStatusMessage(), "마켓에 성공적으로 생성되었습니다.");
        assertEquals(resDto.getData().getClass(), MarketResDto.class);
    }

    @Test
    @DisplayName("마켓 북마크 서비스 테스트")
    void LikeMarketTest(){
        Member member = Member.builder()
                .email("LikeAuctionTest1@example.com")
                .memberType("회원")
                .nickname("LikeAuctionTest1")
                .profilePath("profile/tom.jpg")
                .build();
        Member savedMember = memberRepository.save(member);
        Member member1 = Member.builder()
                .email("LikeAuctionTest2@example.com")
                .memberType("회원")
                .nickname("LikeAuctionTest2")
                .profilePath("profile/tom.jpg")
                .build();
        Member savedMember1 = memberRepository.save(member1);
        Market market = marketRepository.save(
                Market.builder()
                        .seller(savedMember1).market(MarketReqDto.builder().build())
                        .build());
        DataResDto<Boolean> data = (DataResDto<Boolean>) marketService.likeMarket(savedMember, market.getId());
        assertTrue(data.getData());
        assertEquals(data.getStatusCode(), 200);
        DataResDto<Boolean> data1 = (DataResDto<Boolean>) marketService.unlikeMarket(savedMember, market.getId());
        assertTrue(data1.getData());
        assertEquals(data1.getStatusCode(), 204);
    }

    @Test
    @DisplayName("마켓 상세 조회 서비스 테스트")
    void MarketDetailTest() throws Exception {
        Member member = Member.builder()
                .email("MarketDetailTest1@example.com")
                .memberType("회원")
                .nickname("MarketDetailTest1")
                .profilePath("profile/tom.jpg")
                .build();
        Member liker = memberRepository.save(member);
        Member member1 = Member.builder()
                .email("MarketDetailTest1@example.com")
                .memberType("회원")
                .nickname("MarketDetailTest1")
                .profilePath("profile/tom.jpg")
                .build();
        Member savedMember1 = memberRepository.save(member1);
        Market market = marketRepository.save(
                Market.builder()
                        .seller(savedMember1)
                        .market(MarketReqDto.builder()
                                .nftId(BigInteger.valueOf(1L).toString())
                                .build())
                        .build());
        likeMarketRepository.save(LikeMarket.builder().liker(liker).market(market).build());

        DataResDto<MarketDetailDto> res = (DataResDto<MarketDetailDto>) marketService.getMarketDetail(liker, market.getId());
        assertEquals(true, res.getData().getIsLike());
//        assertEquals();
    }
}