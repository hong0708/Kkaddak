package com.example.kkaddak.core.repository;

import com.example.kkaddak.core.entity.Member;
import com.example.kkaddak.core.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SongRepository extends JpaRepository<Song, Integer> {
    Optional<Song> findBySongUuid(UUID songUuid);

    Optional<Song> findById(Integer songId);

    void deleteById(Integer songId);

    @Query("SELECT m FROM Song m ORDER BY m.uploadDate DESC")
    List<Song> findTop10ByOrderByUploadedAtDesc();

    List<Song> findAll();
}