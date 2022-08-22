package com.zerologix.interview.tradeengine.trade.data.dataservice;

import com.zerologix.interview.tradeengine.trade.service.dto.TradeTransaction;

import java.util.List;
import java.util.Optional;

/**
 * The service provide to manipulate TradeTransaction to access Cassandra DB.
 */
public interface TradeTransactionDataService {

    /**
     * Creates and stores a trade transaction to Cassandra DB.
     *
     * @param tradeTransaction the trade transaction
     * @return TradeTransaction
     */
    TradeTransaction createTradeTransaction(TradeTransaction tradeTransaction);

    /**
     * Gets a trade transaction by the given trade transaction id.
     *
     * @param tradeTransactionId the trade transaction id
     * @return an optional of TradeTransaction.
     */
    Optional<TradeTransaction> getTradeTransaction(String tradeTransactionId);

    /**
     * Finds a list of trade transactions by the given customer id.
     *
     * @param customerId the customer id
     * @return a list of trade transactions
     */
    List<TradeTransaction> findTradeTransactionByCustomerId(final String customerId);
}
