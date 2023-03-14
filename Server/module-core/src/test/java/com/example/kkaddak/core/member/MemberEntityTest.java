package com.example.kkaddak.core.member;


import com.example.kkaddak.core.entity.Member;
import com.example.kkaddak.core.exception.IllegalMemberTypeException;
import com.example.kkaddak.core.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class MemberEntityTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("Member 테이블 Enum test")
    void MemberEnumTest1() {
        Member member = Member.builder()
                .email("john@example.com")
                .memberType("회원")
                .nickname("유저1")
                .profilePath("profile/tom.jpg")
                .build();

        Member savedMember = memberRepository.save(member);

        assertEquals(savedMember.getMemberType().name(), "MEMBER");

        Member member2 = Member.builder()
                .email("bob@example.com")
                .memberType("관리자")
                .nickname("관리자1")
                .profilePath("profile/bob.jpg")
                .build();
        Member savedMember2 = memberRepository.save(member2);

        assertEquals(savedMember2.getMemberType().name(), "ADMIN");

        Throwable exception = assertThrows(IllegalMemberTypeException.class, () -> Member.builder()
                .email("bob@example.com")
                .memberType("관리")
                .nickname("관리자1")
                .profilePath("profile/bob.jpg").build());
        assertEquals("'관리자' 또는 '회원'을 입력해주세요.", exception.getMessage());
    }
}