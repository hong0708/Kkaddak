package com.example.kkaddak.api.auction;

import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.api.dto.market.MarketResDto;
import com.example.kkaddak.api.service.MarketService;
import com.example.kkaddak.core.dto.MarketReqDto;
import com.example.kkaddak.core.entity.Market;
import com.example.kkaddak.core.entity.Member;
import com.example.kkaddak.core.repository.MarketRepository;
import com.example.kkaddak.core.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@ActiveProfiles("apitest")
public class auctionServiceTest {

    @Autowired
    MarketService marketService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MarketRepository marketRepository;

    @Test
    @DisplayName("경매 생성 서비스 테스트")
    void CreateAcutionTest(){
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
    @DisplayName("경매 북마크 서비스 테스트")
    void LikeAuctionTest(){
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
}