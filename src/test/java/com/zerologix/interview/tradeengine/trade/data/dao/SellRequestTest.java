package com.zerologix.interview.tradeengine.trade.data.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

class SellRequestTest {
    private static final String REQUEST_ID = "testRequestId";
    private static final String PRODUCT_ID = "testProductId";
    private static final String CUSTOMER_ID = "testCustomerId";
    private static final BigDecimal REQUEST_AMOUNT = new BigDecimal("3.14");
    private static final int REQUEST_QUANTITY = 7;
    private static final Date REQUEST_TIME = new Date();

    @Test
    void testBuilderAndSetterAndGetter() {
        final var sellRequest = SellRequest.builder()
                .requestId(REQUEST_ID)
                .productId(PRODUCT_ID)
                .requestAmount(REQUEST_AMOUNT)
                .requestQuantity(REQUEST_QUANTITY)
                .requestTime(REQUEST_TIME)
                .customerId(CUSTOMER_ID)
                .build();
        Assertions.assertEquals(REQUEST_ID, sellRequest.getRequestId());
        Assertions.assertEquals(PRODUCT_ID, sellRequest.getProductId());
        Assertions.assertEquals(REQUEST_AMOUNT, sellRequest.getRequestAmount());
        Assertions.assertEquals(REQUEST_QUANTITY, sellRequest.getRequestQuantity());
        Assertions.assertEquals(REQUEST_TIME, sellRequest.getRequestTime());
        Assertions.assertEquals(CUSTOMER_ID, sellRequest.getCustomerId());
    }

}