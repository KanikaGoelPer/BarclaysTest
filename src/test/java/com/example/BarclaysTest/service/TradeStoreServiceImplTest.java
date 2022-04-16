package com.example.BarclaysTest.service;

import com.example.BarclaysTest.dto.ResponseDto;
import com.example.BarclaysTest.dto.TradeDto;
import com.example.BarclaysTest.entity.Trade;
import com.example.BarclaysTest.exception.TradeVersionLowerNotSupportedException;
import com.example.BarclaysTest.repo.TradeStoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TradeStoreServiceImplTest {

    @InjectMocks
    TradeStoreServiceImpl tradeStoreService;

    @Mock
    TradeStoreRepository tradeStoreRepository;

    @Test
    public void createNewTradeTest() throws TradeVersionLowerNotSupportedException {
        when(tradeStoreRepository.findByTradeId(anyString())).thenReturn(Collections.emptyList());
        when(tradeStoreRepository.save(any())).thenReturn(new Trade());
        ResponseDto responseDto = tradeStoreService.createTrades(getTradeDto());
        assertEquals("Trade successfully created.",responseDto.getMsg());
    }

    @Test
    public void updateInExistingTrade_IfVersionIsSameTest() throws TradeVersionLowerNotSupportedException {
        List<Trade> trades = new ArrayList<>();
        trades.add(getTrade());
        when(tradeStoreRepository.findByTradeId(anyString())).thenReturn(trades);
        when(tradeStoreRepository.findByTradeIdOrderByVersion(anyString())).thenReturn(trades);
        when(tradeStoreRepository.save(any())).thenReturn(getTrade());
        ResponseDto responseDto = tradeStoreService.createTrades(getTradeDto());
        assertEquals("Trade successfully updated.",responseDto.getMsg());
    }

    @Test
    public void throwException_IfVersionIsLowerThanPrevious() {
        String msg ="";
        List<Trade> trades = new ArrayList<>();
        trades.add(getTrade());
        when(tradeStoreRepository.findByTradeId(anyString())).thenReturn(trades);
        when(tradeStoreRepository.findByTradeIdOrderByVersion(anyString())).thenReturn(trades);
        TradeDto tradeDto = getTradeDto();
        tradeDto.setVersion(-1);
        try{
            tradeStoreService.createTrades(tradeDto);
        }
        catch (Exception e){
           msg = e.getMessage();
        }
        assertEquals("Please check trade version. should be above to existing versions",msg);
    }

    @Test
    public void updateExpiredFlagTest() {
        List<Trade> trades = new ArrayList<>();
        trades.add(getTrade());
        when(tradeStoreRepository.findByMaturityDateLessThanAndExpiredNot(any(), anyChar())).thenReturn(trades);
        List<Trade> response = tradeStoreService.updateExpiredFlag();
        assertNotNull(response);
    }

    TradeDto getTradeDto(){
        TradeDto tradeDto = new TradeDto();
        tradeDto.setTradeId("1");
        tradeDto.setBookId("b11");
        tradeDto.setCounterPartyId("cupid");
        tradeDto.setVersion(1);
        tradeDto.setMaturityDate(new Date());
        return tradeDto;
    }

    Trade getTrade(){
        Trade trade = new Trade();
        trade.setId(1);
        trade.setTradeId("1");
        trade.setBookId("b1");
        trade.setCounterPartyId("cpid");
        trade.setVersion(1);
        trade.setMaturityDate(new Date());
        return trade;
    }
}
