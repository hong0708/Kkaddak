package com.example.kkaddak.core.repository;

import com.example.kkaddak.core.dto.MarketConditionReqDto;
import com.example.kkaddak.core.dto.MarketConditionResDto;
import com.example.kkaddak.core.dto.MarketReqDto;
import com.example.kkaddak.core.entity.Market;
import com.example.kkaddak.core.entity.LikeMarket;
import com.example.kkaddak.core.entity.Member;
import com.example.kkaddak.core.exception.NoContentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class MarketRepositoryTest {
    @Autowired
    EntityManager em;
    @Autowired
    MarketRepository marketRepository;
    @Autowired
    LikeMarketRepository likeMarketRepository;
    @Autowired
    MemberRepository memberRepository;
    @Test
    @DisplayName("동적 쿼리 테스트")
    void AuctionConditionTest() throws NoContentException {
        Member member1 = Member.builder()
                .email("AuctionCondition1@example.com")
                .memberType("회원")
                .nickname("AuctionCondition1")
                .profilePath("profile/tom.jpg")
                .build();
        Member savedMember1 = memberRepository.save(member1);
        Member member2 = Member.builder()
                .email("AuctionCondition2@example.com")
                .memberType("회원")
                .nickname("AuctionCondition2")
                .profilePath("profile/tom.jpg")
                .build();
        Member savedMember2 = memberRepository.save(member2);

        Market market1 = marketRepository.save(Market.builder().seller(savedMember1).market(MarketReqDto.builder().songTitle("market1").build()).build());
        Market market2 = marketRepository.save(Market.builder().seller(savedMember1).market(MarketReqDto.builder().songTitle("market2").build()).build());
        Market market3 = marketRepository.save(Market.builder().seller(savedMember1).market(MarketReqDto.builder().songTitle("market3").build()).build());

        likeMarketRepository.save(LikeMarket.builder().liker(savedMember1).market(market1).build());
        likeMarketRepository.save(LikeMarket.builder().liker(savedMember1).market(market2).build());
        likeMarketRepository.save(LikeMarket.builder().liker(savedMember2).market(market2).build());

        em.flush();
        LikeMarket likeMarket = likeMarketRepository.findByLikerAndMarket(savedMember1, market1).get();
        System.out.println(likeMarket.getMarket().getCreatedAt());
        List<MarketConditionResDto> res = marketRepository.findMarketsByCondition(MarketConditionReqDto.builder().lastId(-1).limit(3).onlySelling(true).build(), member1.getId());

        assertEquals(3, res.size(), "평가1 실패");
        assertEquals(3, res.get(0).getMarketId(), "평가2 실패");
        assertEquals(0L, res.get(0).getCntLikeMarket(), "평가3 실패");

        Market savedAcution3 = marketRepository.findById(3).orElse(null);
        savedAcution3.closeMarket();
        marketRepository.save(savedAcution3);
        List<MarketConditionResDto> res1 = marketRepository.findMarketsByCondition(MarketConditionReqDto.builder().lastId(-1).limit(3).onlySelling(true).build(), member1.getId());
        assertEquals(2, res1.size(), "평가4 실패");
        assertTrue(res1.stream().noneMatch(tuple -> tuple.getMarketId() == 3));
        List<MarketConditionResDto> res2 = marketRepository.findMarketsByCondition(MarketConditionReqDto.builder().lastId(-1).limit(3).onlySelling(false).build(), member1.getId());
        assertEquals(3, res2.size(), "평가6 실패");
        List<MarketConditionResDto> res3 = marketRepository.findMarketsByCondition(MarketConditionReqDto.builder().lastId(3).limit(3).onlySelling(false).build(), member1.getId());
        assertEquals(2, res3.size(), "평가6 실패");
        assertTrue(res3.stream().allMatch(MarketConditionResDto::getIsLike));
        List<MarketConditionResDto> res4 = marketRepository.findMarketsByCondition(MarketConditionReqDto.builder().lastId(3).limit(3).onlySelling(false).build(), member2.getId());
        assertFalse(res4.get(1).getIsLike());

    }
}
