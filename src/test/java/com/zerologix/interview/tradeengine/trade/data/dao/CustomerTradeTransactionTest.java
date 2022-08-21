package com.zerologix.interview.tradeengine.trade.data.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTradeTransactionTest {

    private static final String CUSTOMER_ID = "testCustomerId";
    private static final String TRANSACTION_ID= "testTransactionId";

    @Test
    void testConstructor(){
        final var customerTradeTransaction = new CustomerTradeTransaction(CUSTOMER_ID, TradeType.SELL.name(), TRANSACTION_ID);
        Assertions.assertEquals(CUSTOMER_ID, customerTradeTransaction.getCustomerId());
        Assertions.assertEquals(TradeType.SELL.name(), customerTradeTransaction.getType());
        Assertions.assertEquals(TRANSACTION_ID, customerTradeTransaction.getTransactionId());
    }

    @Test
    void testSetterAndGetter(){
        final var customerTradeTransaction = new CustomerTradeTransaction();
        customerTradeTransaction.setCustomerId(CUSTOMER_ID);
        customerTradeTransaction.setType(TradeType.BUY.name());
        customerTradeTransaction.setTransactionId(TRANSACTION_ID);
        Assertions.assertEquals(CUSTOMER_ID, customerTradeTransaction.getCustomerId());
        Assertions.assertEquals(TradeType.BUY.name(), customerTradeTransaction.getType());
        Assertions.assertEquals(TRANSACTION_ID, customerTradeTransaction.getTransactionId());
    }

}