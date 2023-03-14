package com.example.kkaddak.core.repository;

import com.example.kkaddak.core.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    boolean existsByEmail(String email);
    Optional<Member> findMemberByEmail(String email);

    Optional<Member> findByNicknameAndIdNot(String nickname, Integer id);
}
