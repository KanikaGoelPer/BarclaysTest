package com.example.BarclaysTest.exception;

public class TradeVersionLowerNotSupportedException extends Exception {
    String msg;

    public TradeVersionLowerNotSupportedException(String msg){
        super(msg);
        this.msg =msg;
    }
}
