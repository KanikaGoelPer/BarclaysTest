package com.example.BarclaysTest.service;

import com.example.BarclaysTest.dto.ResponseDto;
import com.example.BarclaysTest.dto.TradeDto;
import com.example.BarclaysTest.entity.Trade;
import com.example.BarclaysTest.exception.TradeVersionLowerNotSupportedException;

import java.util.List;

public interface TradeStoreService {

    ResponseDto createTrades(TradeDto tradeDto) throws TradeVersionLowerNotSupportedException;

    List<Trade> updateExpiredFlag();
}
