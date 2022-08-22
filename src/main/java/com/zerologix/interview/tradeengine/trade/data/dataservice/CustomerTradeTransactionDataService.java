package com.zerologix.interview.tradeengine.trade.data.dataservice;

import com.zerologix.interview.tradeengine.trade.service.dto.TradeTransaction;

/**
 * The service provide to manipulate CustomerTradeTransaction to access Cassandra DB.
 */
public interface CustomerTradeTransactionDataService {

    /**
     * Creates a CustomerTradeTransaction through the given TradeTransaction and stores it into Cassandra DB.
     *
     * @param tradeTransaction the trade transaction
     */
    void createCustomerTradeTransaction(TradeTransaction tradeTransaction);

}
