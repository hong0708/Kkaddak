package com.example.kkaddak.core.repository;

import com.example.kkaddak.core.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicRepository extends JpaRepository<Music, Integer> {
}
