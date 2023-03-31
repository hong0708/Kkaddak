package com.example.kkaddak.core.repository;

import com.example.kkaddak.core.entity.Market;
import com.example.kkaddak.core.entity.LikeMarket;
import com.example.kkaddak.core.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LikeMarketRepository extends JpaRepository<LikeMarket, Integer> {
    List<LikeMarket> findByLiker(Member liker);
    List<LikeMarket> findByMarket(Market market);
    Optional<LikeMarket> findByLikerAndMarket(Member member, Market market);
    Boolean existsByLikerAndMarket(Member liker, Market market);
}
