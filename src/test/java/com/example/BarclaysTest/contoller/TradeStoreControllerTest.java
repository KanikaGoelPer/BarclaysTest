package com.example.BarclaysTest.contoller;

import com.example.BarclaysTest.controller.TradeStoreController;
import com.example.BarclaysTest.dto.ResponseDto;
import com.example.BarclaysTest.dto.TradeDto;
import com.example.BarclaysTest.exception.TradeVersionLowerNotSupportedException;
import com.example.BarclaysTest.service.TradeStoreService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.Objects;

@ExtendWith(MockitoExtension.class)
public class TradeStoreControllerTest {

    @InjectMocks
    TradeStoreController tradeStoreController;

    @Mock
    TradeStoreService tradeStoreService;

    @Test
    void createTradesTest() throws TradeVersionLowerNotSupportedException {
        when(tradeStoreService.createTrades(any())).thenReturn(
                new ResponseDto("Trade successfully created.", HttpStatus.CREATED));
        ResponseEntity<ResponseDto> response = tradeStoreController.createTrades(getTradeDto());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Trade successfully created.", Objects.requireNonNull(response.getBody()).getMsg());
    }

    TradeDto getTradeDto(){
        TradeDto tradeDto = new TradeDto();
        tradeDto.setTradeId("1");
        tradeDto.setBookId("b1");
        tradeDto.setCounterPartyId("cpid");
        tradeDto.setVersion(1);
        tradeDto.setMaturityDate(new Date());
        return tradeDto;
    }
}
