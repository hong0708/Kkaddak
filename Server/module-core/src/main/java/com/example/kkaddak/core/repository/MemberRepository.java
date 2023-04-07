package com.example.kkaddak.core.repository;

import com.example.kkaddak.core.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface MemberRepository extends JpaRepository<Member, Integer>, MemberRepositoryCustom {

    boolean existsByEmail(String email);
    Optional<Member> findMemberByEmail(String email);

    Optional<Member> findByNicknameAndIdNot(String nickname, Integer id);

    Optional<Member> findByUuid(UUID uuid);
    Optional<Member> findByNickname(String nickname);


}
