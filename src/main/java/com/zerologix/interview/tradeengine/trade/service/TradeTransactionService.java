package com.zerologix.interview.tradeengine.trade.service;

import com.zerologix.interview.tradeengine.trade.service.dto.TradeTransaction;

import java.util.List;
import java.util.Optional;

/**
 * TradeTransactionService provides methods to get trade transaction data.
 */
public interface TradeTransactionService {

    /**
     * Gets a trade transaction by the given trade transaction id.
     * If there is no data, it will return an empty Optional.
     *
     * @param tradeTransactionId the trade transaction id
     * @return Optional of TradeTransaction
     */
    Optional<TradeTransaction> getTradeTransaction(final String tradeTransactionId);

    /**
     * Finds a list of trade transactions by the given customer id.
     * If there is no data, it will return an empty list.
     *
     * @param customerId the customer id
     * @return a list of trade transactions
     */
    List<TradeTransaction> findTradeTransactionByCustomerId(final String customerId);
}
