package com.example.kkaddak.core.repository;

import com.example.kkaddak.core.entity.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface PlayListRepository extends JpaRepository<PlayList, Integer> {
}
