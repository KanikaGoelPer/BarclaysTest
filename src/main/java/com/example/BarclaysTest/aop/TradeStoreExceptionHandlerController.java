package com.example.BarclaysTest.aop;

import com.example.BarclaysTest.exception.TradeVersionLowerNotSupportedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@RestControllerAdvice
public class TradeStoreExceptionHandlerController {

    @ExceptionHandler(value = TradeVersionLowerNotSupportedException.class)
    public ResponseEntity<Object> tradeVersionLowerNotSupportedException(TradeVersionLowerNotSupportedException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<Object> internalServerErrorException(HttpServerErrorException.InternalServerError exception) {
        return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = HttpClientErrorException.BadRequest.class)
    public ResponseEntity<Object> badRequestException(HttpClientErrorException.BadRequest exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }


}
