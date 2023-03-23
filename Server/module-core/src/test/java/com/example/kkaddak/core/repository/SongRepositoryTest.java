package com.example.kkaddak.core.repository;

import com.example.kkaddak.core.entity.Member;
import com.example.kkaddak.core.entity.Mood;
import com.example.kkaddak.core.entity.QSong;
import com.example.kkaddak.core.entity.Song;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
public class SongRepositoryTest {
    @Autowired
    SongRepository songRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MoodRepository moodRepository;

    @Autowired
    SearchRepository searchRepository;

    @Test
    void UserTest() {
        Member member = Member.builder()
                .email("john@example.com")
                .memberType("회원")
                .nickname("MemberServiceTest1")
                .profilePath("profile/tom.jpg")
                .build();

        Member savedMember = memberRepository.save(member);

        Mood mood = Mood.builder().
                mood1("hi").mood2("").mood3("").build();

        Mood savedMood = moodRepository.save(mood);

        System.out.println(savedMood);

        Song song = Song.builder()
                .title("hi")
                .songPath("aa")
                .coverPath("bbb")
                .genre("좋은")
                .moods(savedMood)
                .member(savedMember)
                .build();

        Song savedSong = songRepository.save(song);

        System.out.println(songRepository.findTop5ByOrderByUploadDateDesc());
    }
}
