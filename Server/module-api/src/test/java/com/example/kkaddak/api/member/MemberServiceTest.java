package com.example.kkaddak.api.member;

import com.example.kkaddak.api.dto.DataResDto;
import com.example.kkaddak.api.dto.member.*;
import com.example.kkaddak.api.exception.NotFoundException;
import com.example.kkaddak.api.service.MemberService;
import com.example.kkaddak.core.entity.Member;
import com.example.kkaddak.core.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("apitest")
public class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("유저 관련 서비스 테스트")
    void MemberServiceTest1() {
        Member member1 = Member.builder()
                .email("john@example.com")
                .memberType("회원")
                .nickname("MemberServiceTest1")
                .profilePath("profile/tom.jpg")
                .build();
        memberRepository.save(member1);
        Member member2 = Member.builder()
                .email("boby@example.com")
                .memberType("회원")
                .nickname("MemberServiceTest2")
                .profilePath("profile/bom.jpg")
                .build();
        Member savedMember2 = memberRepository.save(member2);

        boolean isExist1 = memberService.checkNicknameDuplicate("MemberServiceTest1", savedMember2);
        boolean isExist2 = memberService.checkNicknameDuplicate("newNickname", savedMember2);
        assertTrue(isExist1);
        assertFalse(isExist2);
    }

    @Test
    @DisplayName("유저 구독 서비스 테스트")
    void FollowTest() {
        Member member1 = Member.builder()
                .email("john@example.com")
                .memberType("회원")
                .nickname("MemberServiceTest1")
                .profilePath("profile/tom.jpg")
                .build();
        Member follower = memberRepository.save(member1);
        Member member2 = Member.builder()
                .email("boby@example.com")
                .memberType("회원")
                .nickname("MemberServiceTest2")
                .profilePath("profile/bom.jpg")
                .build();
        Member followed = memberRepository.save(member2);

        DataResDto<FollowResDto> res = (DataResDto<FollowResDto>) memberService.followMember(follower, followed.getUuid().toString());
        assertEquals(res.getData().getMyFollowers(), 0);
        assertEquals(res.getData().getMyFollwings(), 1);
        DataResDto<FollowResDto> res1 = (DataResDto<FollowResDto>) memberService.followMember(followed, follower.getUuid().toString());
        assertEquals(res1.getData().getMyFollowers(), 1);
        assertEquals(res1.getData().getMyFollwings(), 1);
        DataResDto<FollowResDto> res2 = (DataResDto<FollowResDto>) memberService.unfollowMember(follower, followed.getUuid().toString());
        assertEquals(res2.getData().getMyFollowers(), 1);
        assertEquals(res2.getData().getMyFollwings(), 0);
        DataResDto<FollowResDto> res3 = (DataResDto<FollowResDto>) memberService.unfollowMember(followed, follower.getUuid().toString());
        assertEquals(res3.getData().getMyFollowers(), 0);
        assertEquals(res3.getData().getMyFollwings(), 0);

        assertThrows(NotFoundException.class, () -> memberService.unfollowMember(followed, follower.getUuid().toString()));
    }

    @Test
    @DisplayName("유저 조회 기능 서비스 테스트")
    void ProfileServiceTest(){

        Member member1 = Member.builder()
                .email("john@example.com")
                .memberType("회원")
                .nickname("MemberServiceTest1")
                .profilePath("profile/tom.jpg")
                .build();
        Member savedMember1 = memberRepository.save(member1);
        Member member2 = Member.builder()
                .email("boby@example.com")
                .memberType("회원")
                .nickname("MemberServiceTest2")
                .profilePath("profile/bom.jpg")
                .build();
        Member savedMember2 = memberRepository.save(member2);

        DataResDto<ProfileResDto> res = (DataResDto<ProfileResDto>) memberService.getProfile(savedMember1, savedMember1.getNickname());
        assertEquals(res.getData().getIsMine(), true);
        DataResDto<ProfileResDto> res1 = (DataResDto<ProfileResDto>) memberService.getProfile(savedMember1, savedMember2.getNickname());
        assertEquals(res1.getData().getIsMine(), false);
        DataResDto<ProfileResDto> res2 = (DataResDto<ProfileResDto>) memberService.getProfile(savedMember2, savedMember1.getNickname());
        assertEquals(res2.getData().getIsMine(), false);
        assertThrows(NotFoundException.class, () -> memberService.getProfile(savedMember1, "notFoundException"));
    }

    @Test
    @DisplayName("유저 프로필 수정 서비스 테스트")
    void EditProfileTest() throws Exception {

        Member member1 = Member.builder()
                .email("john@example.com")
                .memberType("회원")
                .nickname("MemberServiceTest1")
                .profilePath("profile/tom.jpg")
                .build();
        Member savedMember1 = memberRepository.save(member1);
        Member member2 = Member.builder()
                .email("boby@example.com")
                .memberType("회원")
                .nickname("MemberServiceTest2")
                .profilePath("profile/bom.jpg")
                .build();
        Member savedMember2 = memberRepository.save(member2);
        DataResDto<MemberResDto> res = (DataResDto<MemberResDto>) memberService.updateProfile(savedMember1,
                EditProfileReqDto.builder().isUpdating(true).nickname("ok").build());
        assertEquals(res.getData().getNickname(), "ok");
        assertEquals(res.getData().getProfilePath(), "");

        DataResDto<MemberResDto> res1 = (DataResDto<MemberResDto>) memberService.updateProfile(savedMember2,
                EditProfileReqDto.builder().isUpdating(false).nickname("ok").build());
        assertEquals(res1.getData().getNickname(), "ok");
        assertEquals(res1.getData().getProfilePath(), "profile/bom.jpg");

    }

    @Test
    @DisplayName("지갑 저장  서비스 테스트")
    void SaveAccountTest() throws Exception {

        Member member1 = Member.builder()
                .email("john@example.com")
                .memberType("회원")
                .nickname("MemberServiceTest1")
                .profilePath("profile/tom.jpg")
                .build();
        Member savedMember1 = memberRepository.save(member1);

        DataResDto<Boolean> res1 = (DataResDto<Boolean>) memberService.saveAccount(savedMember1,
                AccountReqDto.builder().account("accountAddress").build());
        assertEquals(res1.getData(), true);

        Member memberWithAccount = memberRepository.findById(savedMember1.getId()).orElse(null);
        assertEquals(memberWithAccount.getAccount(), "accountAddress");
    }
    @Test
    @DisplayName("홈 프로필 조회 서비스 테스트")
    void HomeProfileTest() throws Exception {

        Member member1 = Member.builder()
                .email("john@example.com")
                .memberType("회원")
                .nickname("homeProfileNickname")
                .profilePath("profile/tom.jpg")
                .build();
        Member savedMember1 = memberRepository.save(member1);

        DataResDto<HomeProfileResDto> res1 = (DataResDto<HomeProfileResDto>) memberService.getMyProfile(savedMember1);
        assertEquals(res1.getData().getMySongs(), 0);
        assertEquals(res1.getData().getNickname(), "homeProfileNickname");
        assertEquals(res1.getStatusCode(), 200);
    }
}