package com.example.BarclaysTest.cron;

import com.example.BarclaysTest.entity.Trade;
import com.example.BarclaysTest.repo.TradeStoreRepository;
import com.example.BarclaysTest.service.TradeStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class Scheduler {

    @Autowired
    TradeStoreService tradeStoreService;

    @Autowired
    TradeStoreRepository tradeStoreRepository;

    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    public void fixedRateSch() {
        List<Trade> trades = tradeStoreService.updateExpiredFlag();
        System.out.println(!trades.isEmpty() ? trades.size() : "empty trades");
        if (!trades.isEmpty()) {
            System.out.println("inside");
            trades.forEach(x -> x.setExpired('Y'));
            tradeStoreRepository.saveAll(trades);
        }
    }
}
