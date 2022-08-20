package com.zerologix.interview.tradeengine.trade.service;

import com.zerologix.interview.tradeengine.trade.service.dto.TradeTransaction;

import java.util.List;
import java.util.Optional;

public interface TradeTransactionService {
    Optional<TradeTransaction> getTradeTransaction(final String tradeTransactionId);

    List<TradeTransaction> findTradeTransactionByCustomerId(final String customerId);
}
