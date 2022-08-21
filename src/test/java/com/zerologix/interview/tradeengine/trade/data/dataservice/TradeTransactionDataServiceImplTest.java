package com.zerologix.interview.tradeengine.trade.data.dataservice;

import com.zerologix.interview.tradeengine.trade.data.dao.CustomerTradeTransaction;
import com.zerologix.interview.tradeengine.trade.data.dao.TradeType;
import com.zerologix.interview.tradeengine.trade.data.repository.CustomerTradeTransactionRepository;
import com.zerologix.interview.tradeengine.trade.data.repository.TradeTransactionRepository;
import com.zerologix.interview.tradeengine.trade.data.transform.TradeTransactionDaoTransform;
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

@ExtendWith(MockitoExtension.class)
class TradeTransactionDataServiceImplTest {
    private static final String TRANSACTION_ID = "testTransactionId";
    private static final String PRODUCT_ID = "testProductId";
    private static final BigDecimal AMOUNT = new BigDecimal("3.14");
    private static final int QUANTITY = 7;
    private static final Date CREATED_TIME = new Date();
    private static final String BUY_REQUEST_ID = "testBuyRequestId";
    private static final String SELL_REQUEST_ID = "testSellRequestId";
    private static final String BUY_CUSTOMER_ID = "testBuyCustomerId";
    private static final String SELL_CUSTOMER_ID = "testSellCustomerId";

    private TradeTransactionDataService tradeTransactionDataService;

    @Mock
    private TradeTransactionRepository tradeTransactionRepository;

    @Mock
    private CustomerTradeTransactionRepository customerTradeTransactionRepository;

    private TradeTransactionDaoTransform tradeTransactionDaoTransform;

    @Mock
    private BuyRequestDataService buyRequestDataService;

    @Mock
    private SellRequestDataService sellRequestDataService;


    @BeforeEach
    void setUp() {
        tradeTransactionDaoTransform = new TradeTransactionDaoTransform(buyRequestDataService, sellRequestDataService);
        Mockito.when(buyRequestDataService.findBuyRequest(BUY_REQUEST_ID)).thenReturn(Optional.of(generateBuyRequest()));
        Mockito.when(sellRequestDataService.findSellRequest(SELL_REQUEST_ID)).thenReturn(Optional.of(generateSellRequest()));
        tradeTransactionDataService = new TradeTransactionDataServiceImpl(tradeTransactionRepository, customerTradeTransactionRepository, tradeTransactionDaoTransform);
    }

    @Test
    void testCreateTradeTransaction() {
        final var tradeTransaction = generateTradeTransaction();
        Mockito.when(tradeTransactionRepository.save(tradeTransactionDaoTransform.transform(tradeTransaction))).thenReturn(tradeTransactionDaoTransform.transform(tradeTransaction));
        tradeTransactionDataService.createTradeTransaction(tradeTransaction);
        Mockito.verify(tradeTransactionRepository).save(tradeTransactionDaoTransform.transform(tradeTransaction));
    }

    @Test
    void testGetTradeTransaction() {
        final var tradeTransaction = generateTradeTransaction();
        Mockito.when(tradeTransactionRepository.findById(TRANSACTION_ID)).thenReturn(Optional.of(tradeTransactionDaoTransform.transform(tradeTransaction)));
        final var tradeTransactionInDBOptional = tradeTransactionDataService.getTradeTransaction(TRANSACTION_ID);
        if (tradeTransactionInDBOptional.isPresent()) {
            final var tradeTransactionInDB = tradeTransactionInDBOptional.get();
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
    void findTradeTransactionByCustomerId() {
        final var customerTradeTransaction = new CustomerTradeTransaction(BUY_CUSTOMER_ID, TradeType.BUY.name(), TRANSACTION_ID);
        Mockito.when(customerTradeTransactionRepository.findByCustomerId(BUY_CUSTOMER_ID)).thenReturn(List.of(customerTradeTransaction));
        final var tradeTransaction = generateTradeTransaction();
        Mockito.when(tradeTransactionRepository.findById(TRANSACTION_ID)).thenReturn(Optional.of(tradeTransactionDaoTransform.transform(tradeTransaction)));
        final var tradeTransactionList = tradeTransactionDataService.findTradeTransactionByCustomerId(BUY_CUSTOMER_ID);
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