package com.example.kkaddak.core.repository;

import com.example.kkaddak.core.entity.Member;
import com.example.kkaddak.core.entity.Song;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
public class SongRepositoryTest {
    @Autowired
    SongRepository songRepository;

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

        Member savedMember = memberRepository.save(member);

        Song song = new Song();
        song.setTitle("hi");
        song.setSongPath("aa");
        song.setCoverPath("bbb");
        song.setGenre("좋은");
        song.setMood("ㅋㅋ");
        song.setMember(savedMember);

        Song savedSong = songRepository.save(song);

        System.out.println(songRepository.findTop10ByOrderByUploadedAtDesc());
    }
}
