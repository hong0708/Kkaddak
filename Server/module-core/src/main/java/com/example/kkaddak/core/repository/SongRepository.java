package com.example.kkaddak.core.repository;

import com.example.kkaddak.core.entity.Member;
import com.example.kkaddak.core.entity.Song;
import com.example.kkaddak.core.entity.SongStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SongRepository extends JpaRepository<Song, Integer> {

    Optional<Song> findBySongUuid(UUID songUuid);

    Optional<Song> findByMemberAndSongUuid(Member member, UUID songUuid);

    List<Song> findByMember(Member member);

    List<Song> findByMemberOrderByUploadDateDesc(Member member);

    void deleteById(Integer songId);

    List<Song> findTop5ByOrderByUploadDateDesc();

    List<Song> findTop12ByOrderByViewsDesc();

    List<Song> findAll();

    Boolean existsByCombination(String combination);

    Integer countByMember(Member member);

    Page<Song> findBySongStatusNotIn(List<SongStatus> songStatuses, Pageable pageable);
}
