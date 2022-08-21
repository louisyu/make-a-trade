package com.zerologix.interview.tradeengine.trade.web.controller;

import com.zerologix.interview.tradeengine.trade.service.SellRequestService;
import com.zerologix.interview.tradeengine.trade.service.dto.SellRequest;
import com.zerologix.interview.tradeengine.trade.web.dto.BuySellRequest;
import com.zerologix.interview.tradeengine.trade.web.dto.BuySellResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.StringUtils;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class SellRequestControllerTest {
    private static final String REQUEST_ID = "testRequestId";
    private static final String PRODUCT_ID = "testProductId";
    private static final String CUSTOMER_ID = "testCustomerId";
    private static final BigDecimal REQUEST_AMOUNT = new BigDecimal("3.14");
    private static final int REQUEST_QUANTITY = 7;
    private static final Date REQUEST_TIME = new Date();

    private SellRequestController controller;

    @Mock
    private SellRequestService sellRequestService;

    @BeforeEach
    void setUp() {
        controller = new SellRequestController(sellRequestService);
    }

    @Test
    void testCreateSellRequest() {
        final var sellBuyRequest = generateBuySellRequest();
        final var sellRequest = generateSellRequest();
        Mockito.when(sellRequestService.createSellRequest(Mockito.any())).thenReturn(sellRequest);
        final var response = controller.createSellRequest(sellBuyRequest);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        final var responseBody = (BuySellResponse) response.getBody();
        Assertions.assertTrue(StringUtils.isNotBlank(responseBody.getRequestId()));
        Assertions.assertNotNull(responseBody.getRequestTime());
        Assertions.assertEquals(PRODUCT_ID,responseBody.getProductId());
        Assertions.assertEquals(REQUEST_QUANTITY, responseBody.getRequestQuantity());
        Assertions.assertEquals(REQUEST_AMOUNT, responseBody.getRequestAmount());
        Assertions.assertEquals(CUSTOMER_ID, responseBody.getCustomerId());

    }

    @Test
    void testCreateSellRequestWhenThrowException() {
        final var buySellRequest = generateBuySellRequest();
        Mockito.when(sellRequestService.createSellRequest(Mockito.any())).thenThrow(new RuntimeException());
        final var response = controller.createSellRequest(buySellRequest);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testGetSellRequest() {
        Mockito.when(sellRequestService.findSellRequest(Mockito.any())).thenReturn(Optional.of(generateSellRequest()));
        final var response = controller.getSellRequest(REQUEST_ID);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        final var responseBody = (BuySellResponse) response.getBody();
        Assertions.assertEquals(REQUEST_ID, responseBody.getRequestId());
        Assertions.assertEquals(PRODUCT_ID, responseBody.getProductId());
        Assertions.assertEquals(REQUEST_AMOUNT, responseBody.getRequestAmount());
        Assertions.assertEquals(REQUEST_QUANTITY, responseBody.getRequestQuantity());
        Assertions.assertEquals(REQUEST_TIME, responseBody.getRequestTime());
    }

    @Test
    void testGetSellRequestWhenThrowException() {
        Mockito.when(sellRequestService.findSellRequest(Mockito.any())).thenThrow(new RuntimeException());
        final var response = controller.getSellRequest(REQUEST_ID);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testGetNoSellRequest() {
        Mockito.when(sellRequestService.findSellRequest(Mockito.any())).thenReturn(Optional.empty());
        final var response = controller.getSellRequest(REQUEST_ID);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteSellRequest() {
        Mockito.when(sellRequestService.deleteSellRequest(Mockito.any())).thenReturn(true);
        final var response = controller.deleteSellRequest(REQUEST_ID);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testDeleteSellRequestWhenThrowException() {
        Mockito.when(sellRequestService.deleteSellRequest(Mockito.any())).thenThrow(new RuntimeException());
        final var response = controller.deleteSellRequest(REQUEST_ID);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testDeleteNoSellRequest() {
        Mockito.when(sellRequestService.deleteSellRequest(Mockito.any())).thenReturn(false);
        final var response = controller.deleteSellRequest(REQUEST_ID);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    BuySellRequest generateBuySellRequest() {
        final var buySellRequest = new BuySellRequest();
        buySellRequest.setCustomerId(CUSTOMER_ID);
        buySellRequest.setProductId(PRODUCT_ID);
        buySellRequest.setRequestAmount(REQUEST_AMOUNT);
        buySellRequest.setRequestQuantity(REQUEST_QUANTITY);
        return buySellRequest;
    }

    private SellRequest generateSellRequest() {
        return SellRequest.builder()
                .requestId(REQUEST_ID)
                .productId(PRODUCT_ID)
                .requestAmount(REQUEST_AMOUNT)
                .requestQuantity(REQUEST_QUANTITY)
                .requestTime(REQUEST_TIME)
                .customerId(CUSTOMER_ID)
                .build();
    }
}