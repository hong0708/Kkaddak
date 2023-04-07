package com.example.kkaddak.core.repository;

import com.example.kkaddak.core.entity.LikeList;
import com.example.kkaddak.core.entity.Member;
import com.example.kkaddak.core.entity.PlayList;
import com.example.kkaddak.core.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayListRepository extends JpaRepository<PlayList, Integer> {

    Boolean existsByMemberAndSong(Member member, Song song);

    Optional<PlayList> findByMemberAndSong(Member member, Song song);

    List<PlayList> findByMemberOrderByAddedDateDesc(Member member);

    List<PlayList> findByMember(Member member);
}
