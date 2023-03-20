package com.example.kkaddak.core.repository;

import com.example.kkaddak.core.entity.LikeList;
import com.example.kkaddak.core.entity.Member;
import com.example.kkaddak.core.entity.PlayList;
import com.example.kkaddak.core.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeListRepository extends JpaRepository<LikeList, Integer> {
    Boolean existsByMemberAndSong(Member member, Song song);

    Optional<LikeList> findByMemberAndSong(Member member, Song song);

    List<LikeList> findByMember(Member member);

}
