package com.example.kkaddak.core.repository;

import com.example.kkaddak.core.entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketRepository extends JpaRepository<Market, Integer>, MarketRepositoryCustom {
}
