package com.example.kkaddak.core.repository;

import com.example.kkaddak.core.entity.Member;
import com.example.kkaddak.core.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SongRepository extends JpaRepository<Song, Integer> {

    Optional<Song> findBySongUuid(UUID songUuid);

    List<Song> findByMember(Member member);

    void deleteById(Integer songId);

    @Query("SELECT m FROM Song m ORDER BY m.uploadDate DESC")
    List<Song> findTop5ByOrderByUploadedAtDesc();

    List<Song> findAll();

}
