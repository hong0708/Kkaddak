package com.example.kkaddak.core.repository;

import com.example.kkaddak.core.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Test
    void UserTest() {
        Member member = Member.builder()
                .email("john@example.com")
                .memberType("회원")
                .nickname("MemberServiceTest1")
                .profilePath("profile/tom.jpg")
                .build();

        memberRepository.save(member);

    }
}
