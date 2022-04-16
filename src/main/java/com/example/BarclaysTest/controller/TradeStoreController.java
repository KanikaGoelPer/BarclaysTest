package com.example.BarclaysTest.controller;

import com.example.BarclaysTest.dto.ResponseDto;
import com.example.BarclaysTest.dto.TradeDto;
import com.example.BarclaysTest.exception.TradeVersionLowerNotSupportedException;
import com.example.BarclaysTest.service.TradeStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TradeStoreController {

    @Autowired
    TradeStoreService tradeStoreService;

    /**
     *
     * @param tradeDto
     * @return
     * @throws TradeVersionLowerNotSupportedException
     */
    @PostMapping(value = "/createTrades")
    public ResponseEntity<ResponseDto> createTrades(@Valid @RequestBody TradeDto tradeDto) throws TradeVersionLowerNotSupportedException {
        ResponseDto responseDto = tradeStoreService.createTrades(tradeDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


}
