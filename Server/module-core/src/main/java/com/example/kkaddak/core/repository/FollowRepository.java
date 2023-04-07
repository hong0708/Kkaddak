package com.example.kkaddak.core.repository;

import com.example.kkaddak.core.entity.Follow;
import com.example.kkaddak.core.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
    List<Follow> findByFollower(Member follower);
    List<Follow> findByFollowing(Member following);
    Optional<Follow> findByFollowerAndFollowing(Member follower, Member following);

    int countByFollower(Member follower);
    int countByFollowing(Member following);
    Boolean existsByFollowerAndFollowing(Member follower, Member following);
}
