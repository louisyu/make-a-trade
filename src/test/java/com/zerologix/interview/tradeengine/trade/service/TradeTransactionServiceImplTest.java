package com.zerologix.interview.tradeengine.trade.service;

import com.zerologix.interview.tradeengine.trade.data.dataservice.TradeTransactionDataService;
import com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest;
import com.zerologix.interview.tradeengine.trade.service.dto.SellRequest;
import com.zerologix.interview.tradeengine.trade.service.dto.TradeTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TradeTransactionServiceImplTest {
    private static final String TRANSACTION_ID = "testTransactionId";
    private static final String PRODUCT_ID = "testProductId";
    private static final BigDecimal AMOUNT = new BigDecimal("3.14");
    private static final int QUANTITY = 7;
    private static final Date CREATED_TIME = new Date();
    private static final String BUY_REQUEST_ID = "testBuyRequestId";
    private static final String SELL_REQUEST_ID = "testSellRequestId";
    private static final String BUY_CUSTOMER_ID = "testBuyCustomerId";
    private static final String SELL_CUSTOMER_ID = "testSellCustomerId";


    private TradeTransactionService tradeTransactionService;

    @Mock
    private TradeTransactionDataService tradeTransactionDataService;

    @BeforeEach
    void setUp() {
        tradeTransactionService = new TradeTransactionServiceImpl(tradeTransactionDataService);
    }

    @Test
    void testGetTradeTransaction() {
        final var tradeTransaction = generateTradeTransaction();
        Mockito.when(tradeTransactionDataService.getTradeTransaction(TRANSACTION_ID)).thenReturn(Optional.of(tradeTransaction));
        final var tradeTransactionOptional = tradeTransactionService.getTradeTransaction(TRANSACTION_ID);
        if (tradeTransactionOptional.isPresent()) {
            final var tradeTransactionInDB = tradeTransactionOptional.get();
            Assertions.assertEquals(TRANSACTION_ID, tradeTransactionInDB.getTransactionId());
            Assertions.assertEquals(PRODUCT_ID, tradeTransactionInDB.getProductId());
            Assertions.assertEquals(AMOUNT, tradeTransactionInDB.getAmount());
            Assertions.assertEquals(QUANTITY, tradeTransactionInDB.getQuantity());
            Assertions.assertEquals(CREATED_TIME, tradeTransactionInDB.getCreatedTime());
            Assertions.assertEquals(generateSellRequest(), tradeTransactionInDB.getSellRequest());
            Assertions.assertEquals(generateBuyRequest(), tradeTransactionInDB.getBuyRequest());
        } else {
            Assertions.fail("It should have a TradeTransaction.");
        }
    }

    @Test
    void testFindTradeTransactionByCustomerId() {
        final var sellTradeTransaction = generateTradeTransaction();
        Mockito.when(tradeTransactionDataService.findTradeTransactionByCustomerId(SELL_CUSTOMER_ID)).thenReturn(List.of(sellTradeTransaction));
        final var tradeTransactionList = tradeTransactionService.findTradeTransactionByCustomerId(SELL_CUSTOMER_ID);
        if (CollectionUtils.isEmpty(tradeTransactionList)) {
            Assertions.fail("It should have a TradeTransaction.");
        } else {
            final var tradeTransactionInDB = tradeTransactionList.get(0);
            Assertions.assertEquals(TRANSACTION_ID, tradeTransactionInDB.getTransactionId());
            Assertions.assertEquals(PRODUCT_ID, tradeTransactionInDB.getProductId());
            Assertions.assertEquals(AMOUNT, tradeTransactionInDB.getAmount());
            Assertions.assertEquals(QUANTITY, tradeTransactionInDB.getQuantity());
            Assertions.assertEquals(CREATED_TIME, tradeTransactionInDB.getCreatedTime());
            Assertions.assertEquals(generateSellRequest(), tradeTransactionInDB.getSellRequest());
            Assertions.assertEquals(generateBuyRequest(), tradeTransactionInDB.getBuyRequest());
        }
    }

    private TradeTransaction generateTradeTransaction() {
        return TradeTransaction.builder()
                .transactionId(TRANSACTION_ID)
                .productId(PRODUCT_ID)
                .amount(AMOUNT)
                .quantity(QUANTITY)
                .createdTime(CREATED_TIME)
                .sellRequest(generateSellRequest())
                .buyRequest(generateBuyRequest())
                .build();
    }

    private BuyRequest generateBuyRequest() {
        return BuyRequest.builder()
                .requestId(BUY_REQUEST_ID)
                .productId(PRODUCT_ID)
                .requestAmount(AMOUNT)
                .requestQuantity(QUANTITY)
                .requestTime(CREATED_TIME)
                .customerId(BUY_CUSTOMER_ID)
                .build();
    }

    private SellRequest generateSellRequest() {
        return SellRequest.builder()
                .requestId(SELL_REQUEST_ID)
                .productId(PRODUCT_ID)
                .requestAmount(AMOUNT)
                .requestQuantity(QUANTITY)
                .requestTime(CREATED_TIME)
                .customerId(SELL_CUSTOMER_ID)
                .build();
    }

}