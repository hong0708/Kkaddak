package com.example.kkaddak.api.auction;

import com.example.kkaddak.api.dto.auction.AuctionResDto;
import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.api.exception.NoContentException;
import com.example.kkaddak.api.service.AuctionService;
import com.example.kkaddak.core.dto.AuctionReqDto;
import com.example.kkaddak.core.entity.Auction;
import com.example.kkaddak.core.entity.Member;
import com.example.kkaddak.core.repository.MemberRepository;
import com.example.kkaddak.core.repository.AuctionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("apitest")
public class auctionServiceTest {

    @Autowired
    AuctionService auctionService;
    @Autowired
    MemberRepository memberRepository;

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

        AuctionReqDto auctionReqDto = AuctionReqDto.builder()
                .creatorName("songCreator")
                .songTitle("songTitle")
                .nftId("nftIdFromContract")
                .expiredDate(LocalDate.now())
                .nftImagePath("imagePath")
                .bidStartPrice(0.1)
                .build();

        DataResDto resDto = auctionService.createAuction(auctionReqDto, savedMember);

        assertEquals(resDto.getStatusCode(), 200);
        assertEquals(resDto.getStatusMessage(), "옥션이 성공적으로 생성되었습니다.");
        assertEquals(resDto.getData().getClass(), AuctionResDto.class);
    }



    @Autowired
    AuctionRepository auctionRepository;

    @Test
    @DisplayName("경매 조회 query dsl 테스트")
    void AuctionRepositoryTest() throws Exception {
        auctionRepository.save(Auction.builder().auction(AuctionReqDto.builder().songTitle("auction1").expiredDate(LocalDate.now()).build()).build());
        auctionRepository.save(Auction.builder().auction(AuctionReqDto.builder().songTitle("auction2").expiredDate(LocalDate.now()).build()).build());
        auctionRepository.save(Auction.builder().auction(AuctionReqDto.builder().songTitle("auction3").expiredDate(LocalDate.now()).build()).build());

        DataResDto<List<AuctionResDto>> auctions1 = (DataResDto<List<AuctionResDto>>) auctionService.getAuctionAllByPaging(4L, -1L);
        assertEquals(auctions1.getData().size(), 3);

        DataResDto<List<AuctionResDto>> auctions2 = (DataResDto<List<AuctionResDto>>) auctionService.getAuctionAllByPaging(1L, 3L);
        assertEquals(auctions2.getData().get(0).getAuctionId(), 2);

        DataResDto<List<AuctionResDto>> auctions3 = (DataResDto<List<AuctionResDto>>) auctionService.getAuctionAllByPaging(1L, 2L);
        assertEquals(auctions3.getData().get(0).getAuctionId(), 1);

        assertThrows(NoContentException.class, () -> auctionService.getAuctionAllByPaging(1L, 1L));
    }
}