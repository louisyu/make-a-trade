package com.zerologix.interview.tradeengine.trade.web.controller;

import com.zerologix.interview.tradeengine.trade.service.BuyRequestService;
import com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest;
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
class BuyRequestControllerTest {
    private static final String REQUEST_ID = "testRequestId";
    private static final String PRODUCT_ID = "testProductId";
    private static final String CUSTOMER_ID = "testCustomerId";
    private static final BigDecimal REQUEST_AMOUNT = new BigDecimal("3.14");
    private static final int REQUEST_QUANTITY = 7;
    private static final Date REQUEST_TIME = new Date();

    private BuyRequestController controller;

    @Mock
    private BuyRequestService buyRequestService;

    @BeforeEach
    void setUp() {
        controller = new BuyRequestController(buyRequestService);
    }

    @Test
    void testCreateBuyRequest() {
        final var sellBuyRequest = generateBuySellRequest();
        final var buyRequest = generateBuyRequest();
        Mockito.when(buyRequestService.createBuyRequest(Mockito.any())).thenReturn(buyRequest);
        final var response = controller.createBuyRequest(sellBuyRequest);
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
    void testCreateBuyRequestWhenThrowException() {
        final var buySellRequest = generateBuySellRequest();
        Mockito.when(buyRequestService.createBuyRequest(Mockito.any())).thenThrow(new RuntimeException());
        final var response = controller.createBuyRequest(buySellRequest);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testGetBuyRequest() {
        Mockito.when(buyRequestService.findBuyRequest(Mockito.any())).thenReturn(Optional.of(generateBuyRequest()));
        final var response = controller.getBuyRequest(REQUEST_ID);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        final var responseBody = (BuySellResponse) response.getBody();
        Assertions.assertEquals(REQUEST_ID, responseBody.getRequestId());
        Assertions.assertEquals(PRODUCT_ID, responseBody.getProductId());
        Assertions.assertEquals(REQUEST_AMOUNT, responseBody.getRequestAmount());
        Assertions.assertEquals(REQUEST_QUANTITY, responseBody.getRequestQuantity());
        Assertions.assertEquals(REQUEST_TIME, responseBody.getRequestTime());
    }

    @Test
    void testGetBuyRequestWhenThrowException() {
        Mockito.when(buyRequestService.findBuyRequest(Mockito.any())).thenThrow(new RuntimeException());
        final var response = controller.getBuyRequest(REQUEST_ID);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testGetNoBuyRequest() {
        Mockito.when(buyRequestService.findBuyRequest(Mockito.any())).thenReturn(Optional.empty());
        final var response = controller.getBuyRequest(REQUEST_ID);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteBuyRequest() {
        Mockito.when(buyRequestService.deleteBuyRequest(Mockito.any())).thenReturn(true);
        final var response = controller.deleteBuyRequest(REQUEST_ID);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testDeleteBuyRequestWhenThrowException() {
        Mockito.when(buyRequestService.deleteBuyRequest(Mockito.any())).thenThrow(new RuntimeException());
        final var response = controller.deleteBuyRequest(REQUEST_ID);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testDeleteNoBuyRequest() {
        Mockito.when(buyRequestService.deleteBuyRequest(Mockito.any())).thenReturn(false);
        final var response = controller.deleteBuyRequest(REQUEST_ID);
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

    private BuyRequest generateBuyRequest() {
        return BuyRequest.builder()
                .requestId(REQUEST_ID)
                .productId(PRODUCT_ID)
                .requestAmount(REQUEST_AMOUNT)
                .requestQuantity(REQUEST_QUANTITY)
                .requestTime(REQUEST_TIME)
                .customerId(CUSTOMER_ID)
                .build();
    }
}