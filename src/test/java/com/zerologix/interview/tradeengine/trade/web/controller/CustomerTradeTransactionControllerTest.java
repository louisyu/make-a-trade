package com.zerologix.interview.tradeengine.trade.web.controller;

import com.zerologix.interview.tradeengine.trade.service.TradeTransactionService;
import com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest;
import com.zerologix.interview.tradeengine.trade.service.dto.SellRequest;
import com.zerologix.interview.tradeengine.trade.service.dto.TradeTransaction;
import com.zerologix.interview.tradeengine.trade.web.dto.TradeTransactionResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CustomerTradeTransactionControllerTest {
    private static final String TRANSACTION_ID = "testTransactionId";
    private static final String PRODUCT_ID = "testProductId";
    private static final BigDecimal AMOUNT = new BigDecimal("3.14");
    private static final int QUANTITY = 7;
    private static final Date CREATED_TIME = new Date();
    private static final BuyRequest BUY_REQUEST = BuyRequest.builder().requestId("testBuyRequestId").customerId("testBuyerId").build();
    private static final SellRequest SELL_REQUEST = SellRequest.builder().requestId("testSellRequestId").customerId("testSellerId").build();

    private CustomerTradeTransactionController controller;

    @Mock
    private TradeTransactionService tradeTransactionService;

    @BeforeEach
    void setUp() {
        controller = new CustomerTradeTransactionController(tradeTransactionService);
    }

    @Test
    void findTradeTransactionsByCustomerId() {
        final var tradeTransaction = generateTradeTransaction();
        Mockito.when(tradeTransactionService.findTradeTransactionByCustomerId(Mockito.any())).thenReturn(List.of(tradeTransaction));
        final var response = controller.findTradeTransactionsByCustomerId("testBuyerId");
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        final var responseBodyList = (List<TradeTransactionResponse>) response.getBody();
        final var responseBody = responseBodyList.get(0);
        Assertions.assertEquals(TRANSACTION_ID, responseBody.getTransactionId());
        Assertions.assertEquals(PRODUCT_ID, responseBody.getProductId());
        Assertions.assertEquals(AMOUNT, responseBody.getAmount());
        Assertions.assertEquals(QUANTITY, responseBody.getQuantity());
        Assertions.assertEquals(CREATED_TIME, responseBody.getCreatedTime());

    }

    @Test
    void testGetTradeTransactionWhenThrowException() {
        Mockito.when(tradeTransactionService.findTradeTransactionByCustomerId(Mockito.any())).thenThrow(new RuntimeException());
        final var response = controller.findTradeTransactionsByCustomerId("testBuyerId");
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testGetNoTradeTransaction() {
        Mockito.when(tradeTransactionService.findTradeTransactionByCustomerId(Mockito.any())).thenReturn(Collections.emptyList());
        final var response = controller.findTradeTransactionsByCustomerId("testBuyerId");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    private TradeTransaction generateTradeTransaction() {
        return TradeTransaction.builder()
                .transactionId(TRANSACTION_ID)
                .productId(PRODUCT_ID)
                .amount(AMOUNT)
                .quantity(QUANTITY)
                .createdTime(CREATED_TIME)
                .sellRequest(SELL_REQUEST)
                .buyRequest(BUY_REQUEST)
                .build();
    }
}