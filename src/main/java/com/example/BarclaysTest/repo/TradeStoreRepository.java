package com.example.BarclaysTest.repo;

import com.example.BarclaysTest.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TradeStoreRepository extends JpaRepository<Trade,Integer> {
    List<Trade> findByTradeId(String tradeId);

    List<Trade> findByTradeIdOrderByVersion(String tradeId);

    List<Trade> findByMaturityDateLessThanAndExpiredNot(Date date, char y);
}
