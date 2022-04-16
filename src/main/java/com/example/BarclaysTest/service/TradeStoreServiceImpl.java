package com.example.BarclaysTest.service;

import com.example.BarclaysTest.dto.ResponseDto;
import com.example.BarclaysTest.dto.TradeDto;
import com.example.BarclaysTest.entity.Trade;
import com.example.BarclaysTest.exception.TradeVersionLowerNotSupportedException;
import com.example.BarclaysTest.repo.TradeStoreRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TradeStoreServiceImpl implements TradeStoreService {

    @Autowired
    TradeStoreRepository tradeStoreRepository;

    /**
     * create trades and update trade
     * @param tradeDto
     * @return ResponseDto object
     * @throws TradeVersionLowerNotSupportedException
     */
    @Override
    public ResponseDto createTrades(TradeDto tradeDto) throws TradeVersionLowerNotSupportedException {
        ModelMapper modelMapper = getModelMapper();
        ResponseDto responseDto = new ResponseDto();
        ResponseDto response = updateInExistingTrade(tradeDto, responseDto);
        if (response != null) return response;
        return createNewTrade(tradeDto, modelMapper, responseDto);
    }

    /**
     *
     * @param tradeDto
     * @param modelMapper
     * @param responseDto
     * @return new Trade
     */
    private ResponseDto createNewTrade(TradeDto tradeDto, ModelMapper modelMapper, ResponseDto responseDto) {
        //mapping tradeDto to trade
        Trade trade = modelMapper.map(tradeDto, Trade.class);
        trade.setExpired('N');
        trade.setCreatedDate(new Date());
        tradeStoreRepository.save(trade);
        responseDto.setHttpStatus(HttpStatus.CREATED);
        responseDto.setMsg("Trade successfully created.");
        return responseDto;
    }

    /**
     * update trade if already exists
     * @param tradeDto
     * @param responseDto
     * @return ResponseDto object
     * @throws TradeVersionLowerNotSupportedException
     */
    private ResponseDto updateInExistingTrade(TradeDto tradeDto, ResponseDto responseDto) throws TradeVersionLowerNotSupportedException {
        List<Trade> existingTrade = tradeStoreRepository.findByTradeId(tradeDto.getTradeId());
        List<Trade> trades = null;
        if(!existingTrade.isEmpty()){
           trades = validationLogic(tradeDto);
        }
        if(trades!=null){
           Optional<Trade> existTrade =  trades.stream().filter(t-> t.getVersion()== tradeDto.getVersion()).findFirst();
           if(existTrade.isPresent()){
               existTrade.get().setCounterPartyId(tradeDto.getCounterPartyId());
               existTrade.get().setBookId(tradeDto.getBookId());
               existTrade.get().setMaturityDate(tradeDto.getMaturityDate());
               existTrade.get().setExpired('N');
               tradeStoreRepository.save(existTrade.get());
               responseDto.setHttpStatus(HttpStatus.ACCEPTED);
               responseDto.setMsg("Trade successfully updated.");
               return responseDto;
           }
        }
        return null;
    }

    /**
     * validation if new version of existing trade is less than current trade's version
     * @param tradeDto
     * @return
     * @throws TradeVersionLowerNotSupportedException
     */
    private List<Trade> validationLogic(TradeDto tradeDto) throws TradeVersionLowerNotSupportedException {
        List<Trade> trades = tradeStoreRepository.findByTradeIdOrderByVersion(tradeDto.getTradeId());
        if(!trades.isEmpty()){
            Optional<Trade> lowerVersionTrade =  trades.stream().findFirst();
            if(tradeDto.getVersion()<lowerVersionTrade.get().getVersion()){
                throw new TradeVersionLowerNotSupportedException("Please check trade version. should be above to existing versions");
            }
        }
        return trades;
    }

    /**
     *
     * @return ModelMapper object
     */
    private ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    /**
     *
     * @return list of trades for which maturity date is less than today date
     */
    @Override
    public List<Trade> updateExpiredFlag() {
        return tradeStoreRepository.findByMaturityDateLessThanAndExpiredNot(new Date(),'Y');
    }

}
