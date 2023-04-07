package com.example.kkaddak.core.repository;

import com.example.kkaddak.core.entity.Mood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoodRepository extends JpaRepository<Mood, Integer> {
}
