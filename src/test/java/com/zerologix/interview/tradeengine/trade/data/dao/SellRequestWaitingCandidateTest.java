package com.zerologix.interview.tradeengine.trade.data.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

class SellRequestWaitingCandidateTest {
    private static final String SELL_REQUEST_ID = "testRequestId";
    private static final String PRODUCT_ID = "testProductId";
    private static final String CUSTOMER_ID = "testCustomerId";
    private static final BigDecimal REQUEST_AMOUNT = new BigDecimal("3.14");
    private static final int REQUEST_QUANTITY = 7;
    private static final Date REQUEST_TIME = new Date();

    @Test
    void testBuilderAndSetterAndGetter() {
        final var sellRequestWaitingCandidate = SellRequestWaitingCandidate.builder()
                .productId(PRODUCT_ID)
                .amount(REQUEST_AMOUNT)
                .quantity(REQUEST_QUANTITY)
                .requestTime(REQUEST_TIME)
                .sellRequestId(SELL_REQUEST_ID)
                .customerId(CUSTOMER_ID)
                .build();

        Assertions.assertEquals(PRODUCT_ID, sellRequestWaitingCandidate.getProductId());
        Assertions.assertEquals(REQUEST_AMOUNT, sellRequestWaitingCandidate.getAmount());
        Assertions.assertEquals(REQUEST_QUANTITY, sellRequestWaitingCandidate.getQuantity());
        Assertions.assertEquals(REQUEST_TIME, sellRequestWaitingCandidate.getRequestTime());
        Assertions.assertEquals(SELL_REQUEST_ID, sellRequestWaitingCandidate.getSellRequestId());
        Assertions.assertEquals(CUSTOMER_ID, sellRequestWaitingCandidate.getCustomerId());
    }
}