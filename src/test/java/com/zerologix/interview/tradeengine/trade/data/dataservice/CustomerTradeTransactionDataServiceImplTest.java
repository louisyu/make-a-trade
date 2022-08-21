package com.zerologix.interview.tradeengine.trade.data.dataservice;

import com.zerologix.interview.tradeengine.trade.data.dao.CustomerTradeTransaction;
import com.zerologix.interview.tradeengine.trade.data.dao.TradeType;
import com.zerologix.interview.tradeengine.trade.data.repository.CustomerTradeTransactionRepository;
import com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest;
import com.zerologix.interview.tradeengine.trade.service.dto.SellRequest;
import com.zerologix.interview.tradeengine.trade.service.dto.TradeTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerTradeTransactionDataServiceImplTest {
    private static final String TRANSACTION_ID = "testTransactionId";
    private static final String PRODUCT_ID = "testProductId";
    private static final BigDecimal AMOUNT = new BigDecimal("3.14");
    private static final int QUANTITY = 7;
    private static final Date CREATED_TIME = new Date();
    private static final BuyRequest BUY_REQUEST = BuyRequest.builder().customerId("buyCustomerId").requestId("testBuyRequestId").build();
    private static final SellRequest SELL_REQUEST = SellRequest.builder().customerId("sellCustomerId").requestId("testSellRequestId").build();

    @Mock
    private CustomerTradeTransactionRepository customerTradeTransactionRepository;

    private CustomerTradeTransactionDataService customerTradeTransactionDataService;

    @BeforeEach
    void setUp(){
        customerTradeTransactionDataService = new CustomerTradeTransactionDataServiceImpl(customerTradeTransactionRepository);
    }
    @Test
    void testCreateCustomerTradeTransaction() {
        final var tradeTransaction = generateTradeTransaction();
        customerTradeTransactionDataService.createCustomerTradeTransaction(tradeTransaction);
        final var buyCustomerTradeTransaction = new CustomerTradeTransaction(tradeTransaction.getBuyRequest().getCustomerId(), TradeType.BUY.name(), tradeTransaction.getTransactionId());
        final var sellCustomerTradeTransaction = new CustomerTradeTransaction(tradeTransaction.getSellRequest().getCustomerId(), TradeType.SELL.name(), tradeTransaction.getTransactionId());
        Mockito.verify(customerTradeTransactionRepository).save(buyCustomerTradeTransaction);
        Mockito.verify(customerTradeTransactionRepository).save(sellCustomerTradeTransaction);

    }

    private TradeTransaction generateTradeTransaction(){
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