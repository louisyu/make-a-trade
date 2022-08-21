package com.zerologix.interview.tradeengine.trade.service.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

class TradeTransactionTest {
    private static final String TRANSACTION_ID = "testTransactionId";
    private static final String PRODUCT_ID = "testProductId";
    private static final BigDecimal AMOUNT = new BigDecimal("3.14");
    private static final int QUANTITY = 7;
    private static final Date CREATED_TIME = new Date();
    private static final BuyRequest BUY_REQUEST = BuyRequest.builder().requestId("testBuyRequestId").build();
    private static final SellRequest SELL_REQUEST = SellRequest.builder().requestId("testSellRequestId").build();

    @Test
    void testBuilderAndSetterAndGetter() {
        final var tradeTransaction = TradeTransaction.builder()
                .transactionId(TRANSACTION_ID)
                .productId(PRODUCT_ID)
                .amount(AMOUNT)
                .quantity(QUANTITY)
                .createdTime(CREATED_TIME)
                .sellRequest(SELL_REQUEST)
                .buyRequest(BUY_REQUEST)
                .build();
        Assertions.assertEquals(TRANSACTION_ID, tradeTransaction.getTransactionId());
        Assertions.assertEquals(PRODUCT_ID, tradeTransaction.getProductId());
        Assertions.assertEquals(AMOUNT, tradeTransaction.getAmount());
        Assertions.assertEquals(QUANTITY, tradeTransaction.getQuantity());
        Assertions.assertEquals(CREATED_TIME, tradeTransaction.getCreatedTime());
        Assertions.assertEquals(SELL_REQUEST, tradeTransaction.getSellRequest());
        Assertions.assertEquals(BUY_REQUEST, tradeTransaction.getBuyRequest());
    }
}