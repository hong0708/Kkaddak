package com.example.kkaddak.core.repository;

import com.example.kkaddak.core.dto.AuctionConditionReqDto;
import com.example.kkaddak.core.dto.AuctionConditionResDto;
import com.example.kkaddak.core.dto.AuctionReqDto;
import com.example.kkaddak.core.entity.Auction;
import com.example.kkaddak.core.entity.LikeAuction;
import com.example.kkaddak.core.entity.Member;
import com.example.kkaddak.core.exception.NoContentException;
import com.querydsl.core.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class AuctionRepositoryTest {
    @Autowired
    AuctionRepository auctionRepository;
    @Autowired
    LikeAuctionRepository likeAuctionRepository;
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

        Auction auction1 = auctionRepository.save(Auction.builder().auction(AuctionReqDto.builder().songTitle("auction1").expiredDate(String.valueOf(LocalDate.now())).build()).build());
        Auction auction2 = auctionRepository.save(Auction.builder().auction(AuctionReqDto.builder().songTitle("auction2").expiredDate(String.valueOf(LocalDate.now())).build()).build());
        Auction auction3 = auctionRepository.save(Auction.builder().auction(AuctionReqDto.builder().songTitle("auction3").expiredDate(String.valueOf(LocalDate.now())).build()).build());

        likeAuctionRepository.save(LikeAuction.builder().liker(savedMember1).auction(auction1).build());
        likeAuctionRepository.save(LikeAuction.builder().liker(savedMember1).auction(auction2).build());
        likeAuctionRepository.save(LikeAuction.builder().liker(savedMember2).auction(auction2).build());

        List<AuctionConditionResDto> res = auctionRepository.findAuctionsByCondition(AuctionConditionReqDto.builder().lastId(-1).limit(3).onlySelling(true).build(), member1.getId());

        assertEquals(3, res.size(), "평가1 실패");
        assertEquals(3, res.get(0).getAuctionId(), "평가2 실패");
        assertEquals(0L, res.get(0).getCntLikeAuction(), "평가3 실패");

        Auction savedAcution3 = auctionRepository.findById(3).orElse(null);
        savedAcution3.closeAuction();
        auctionRepository.save(savedAcution3);
        List<AuctionConditionResDto> res1 = auctionRepository.findAuctionsByCondition(AuctionConditionReqDto.builder().lastId(-1).limit(3).onlySelling(true).build(), member1.getId());
        assertEquals(2, res1.size(), "평가4 실패");
        assertTrue(res1.stream().noneMatch(tuple -> tuple.getAuctionId() == 3));
        List<AuctionConditionResDto> res2 = auctionRepository.findAuctionsByCondition(AuctionConditionReqDto.builder().lastId(-1).limit(3).onlySelling(false).build(), member1.getId());
        assertEquals(3, res2.size(), "평가6 실패");
        List<AuctionConditionResDto> res3 = auctionRepository.findAuctionsByCondition(AuctionConditionReqDto.builder().lastId(3).limit(3).onlySelling(false).build(), member1.getId());
        assertEquals(2, res3.size(), "평가6 실패");
        assertTrue(res3.stream().allMatch(AuctionConditionResDto::getIsLike));
        List<AuctionConditionResDto> res4 = auctionRepository.findAuctionsByCondition(AuctionConditionReqDto.builder().lastId(3).limit(3).onlySelling(false).build(), member2.getId());
        assertFalse(res4.get(1).getIsLike());

    }
}
