package com.zerologix.interview.tradeengine.trade.data.dataservice;

import com.zerologix.interview.tradeengine.trade.service.dto.TradeTransaction;

import java.util.List;
import java.util.Optional;

public interface TradeTransactionDataService {
    TradeTransaction createTradeTransaction(TradeTransaction tradeTransaction);

    Optional<TradeTransaction> getTradeTransaction(String tradeTransactionId);

    List<TradeTransaction> findTradeTransactionByCustomerId(final String customerId);
}
