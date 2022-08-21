package com.zerologix.interview.tradeengine.trade.service.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

class BuyRequestTest {

    private static final String REQUEST_ID = "testRequestId";
    private static final String PRODUCT_ID = "testProductId";
    private static final String CUSTOMER_ID = "testCustomerId";
    private static final BigDecimal REQUEST_AMOUNT = new BigDecimal("3.14");
    private static final int REQUEST_QUANTITY = 7;
    private static final Date REQUEST_TIME = new Date();

    @Test
    void testBuilderAndSetterAndGetter() {
        final var buyRequest = BuyRequest.builder()
                .requestId(REQUEST_ID)
                .productId(PRODUCT_ID)
                .requestAmount(REQUEST_AMOUNT)
                .requestQuantity(REQUEST_QUANTITY)
                .requestTime(REQUEST_TIME)
                .customerId(CUSTOMER_ID)
                .build();
        Assertions.assertEquals(REQUEST_ID, buyRequest.getRequestId());
        Assertions.assertEquals(PRODUCT_ID, buyRequest.getProductId());
        Assertions.assertEquals(REQUEST_AMOUNT, buyRequest.getRequestAmount());
        Assertions.assertEquals(REQUEST_QUANTITY, buyRequest.getRequestQuantity());
        Assertions.assertEquals(REQUEST_TIME, buyRequest.getRequestTime());
        Assertions.assertEquals(CUSTOMER_ID, buyRequest.getCustomerId());
    }

}