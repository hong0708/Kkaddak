package com.example.kkaddak.core.repository;

import com.example.kkaddak.core.entity.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AuctionRepository extends JpaRepository<Auction, Integer>, AuctionRepositoryCustom {
}
