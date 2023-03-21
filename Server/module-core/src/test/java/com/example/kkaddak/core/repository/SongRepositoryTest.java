package com.example.kkaddak.core.repository;

import com.example.kkaddak.core.entity.Member;
import com.example.kkaddak.core.entity.Mood;
import com.example.kkaddak.core.entity.Song;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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

        Song song = Song.builder()
                .title("hi")
                .songPath("aa")
                .coverPath("bbb")
                .genre("좋은")
                .moods(savedMood)
                .member(savedMember)
                .build();

        Song savedSong = songRepository.save(song);
    }
}
