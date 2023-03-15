package com.example.kkaddak.api.member;

import com.example.kkaddak.api.service.MemberService;
import com.example.kkaddak.core.entity.Member;
import com.example.kkaddak.core.exception.IllegalMemberTypeException;
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
    @DisplayName("Member service test")
    void MemberServiceTest1() {
//        Member member1 = Member.builder()
//                .email("john@example.com")
//                .memberType("회원")
//                .nickname("MemberServiceTest1")
//                .profilePath("profile/tom.jpg")
//                .build();
//        memberRepository.save(member1);
//        Member member2 = Member.builder()
//                .email("boby@example.com")
//                .memberType("회원")
//                .nickname("MemberServiceTest2")
//                .profilePath("profile/bom.jpg")
//                .build();
//        Member savedMember2 = memberRepository.save(member2);
//
//        boolean isExist1 = memberService.checkNicknameDuplicate("MemberServiceTest1", savedMember2);
//        boolean isExist2 = memberService.checkNicknameDuplicate("newNickname", savedMember2);
//        assertTrue(isExist1);
//        assertFalse(isExist2);
    }
}