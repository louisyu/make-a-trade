package com.zerologix.interview.tradeengine.trade.service;

import com.zerologix.interview.tradeengine.trade.data.dataservice.TradeTransactionDataService;
import com.zerologix.interview.tradeengine.trade.service.dto.TradeTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeTransactionServiceImpl implements TradeTransactionService {
    private final TradeTransactionDataService tradeTransactionDataService;

    @Autowired
    public TradeTransactionServiceImpl(final TradeTransactionDataService tradeTransactionDataService) {
        this.tradeTransactionDataService = tradeTransactionDataService;
    }

    @Override
    public Optional<TradeTransaction> getTradeTransaction(final String tradeTransactionId) {
        return tradeTransactionDataService.getTradeTransaction(tradeTransactionId);
    }

    @Override
    public List<TradeTransaction> findTradeTransactionByCustomerId(final String customerId) {
        return tradeTransactionDataService.findTradeTransactionByCustomerId(customerId);
    }
}
