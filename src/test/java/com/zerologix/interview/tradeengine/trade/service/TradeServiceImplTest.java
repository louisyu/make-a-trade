package com.zerologix.interview.tradeengine.trade.service;

import com.zerologix.interview.tradeengine.messagequeue.service.MessageQueuePublishService;
import com.zerologix.interview.tradeengine.messagequeue.service.MessageQueuePublishServiceImpl;
import com.zerologix.interview.tradeengine.messagequeue.source.InMemoryMessageQueue;
import com.zerologix.interview.tradeengine.trade.data.dataservice.CustomerTradeTransactionDataService;
import com.zerologix.interview.tradeengine.trade.data.dataservice.SellRequestWaitingCandidateDataService;
import com.zerologix.interview.tradeengine.trade.data.dataservice.TradeTransactionDataService;
import com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest;
import com.zerologix.interview.tradeengine.trade.service.dto.SellRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

@ExtendWith(MockitoExtension.class)
class TradeServiceImplTest {
    private static final String SELL_REQUEST_ID = "testSellRequestId";
    private static final String BUY_REQUEST_ID = "testBuyRequestId";
    private static final String PRODUCT_ID = "testProductId";
    private static final String CUSTOMER_ID = "testCustomerId";
    private static final BigDecimal REQUEST_AMOUNT = new BigDecimal("3.14");
    private static final int REQUEST_QUANTITY = 7;
    private static final Date REQUEST_TIME = new Date();

    @Mock
    private SellRequestWaitingCandidateDataService sellRequestWaitingCandidateDataService;

    @Mock
    private TradeTransactionDataService tradeTransactionDataService;

    @Mock
    private CustomerTradeTransactionDataService customerTradeTransactionDataService;

    private ConcurrentLinkedQueue<BuyRequest> queue;
    private InMemoryMessageQueue inMemoryMessageQueue;
    private MessageQueuePublishService messageQueuePublishService;

    private TradeService tradeService;

    @BeforeEach
    void setUp() {
        queue = new ConcurrentLinkedQueue<>();
        this.inMemoryMessageQueue = new InMemoryMessageQueue() {
            @Override
            public void addBuyRequestInQueue(final BuyRequest buyRequest) {
                queue.add(buyRequest);
            }

            @Override
            public Optional<BuyRequest> pollBuyRequestInQueue() {
                return Optional.ofNullable(queue.poll());
            }

            @Override
            public void removeBuyRequestInQueue(final BuyRequest buyRequest) {
                queue.remove(buyRequest);
            }
        };
        messageQueuePublishService = new MessageQueuePublishServiceImpl(inMemoryMessageQueue);

        tradeService = new TradeServiceImpl(sellRequestWaitingCandidateDataService, tradeTransactionDataService, customerTradeTransactionDataService, messageQueuePublishService);
    }

    @Test
    void testBuyRequestExpired() {
        final var calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);

        final var buyRequest = BuyRequest.builder().requestTime(calendar.getTime()).build();
        tradeService.trade(buyRequest);

        Mockito.verify(this.tradeTransactionDataService, Mockito.never()).createTradeTransaction(Mockito.any());
        Mockito.verify(customerTradeTransactionDataService, Mockito.never()).createCustomerTradeTransaction(Mockito.any());
        Mockito.verify(this.sellRequestWaitingCandidateDataService, Mockito.never()).allocateSellRequests(buyRequest);

    }

    @Test
    void testExceptionWhenAllocateSellRequests() {
        final var buyRequest = generateBuyRequest();
        Mockito.when(this.sellRequestWaitingCandidateDataService.allocateSellRequests(buyRequest)).thenThrow(new RuntimeException());

        tradeService.trade(buyRequest);

        Assertions.assertEquals(buyRequest, this.inMemoryMessageQueue.pollBuyRequestInQueue().orElse(null));
        Mockito.verify(this.tradeTransactionDataService, Mockito.never()).createTradeTransaction(Mockito.any());
        Mockito.verify(customerTradeTransactionDataService, Mockito.never()).createCustomerTradeTransaction(Mockito.any());

    }

    @Test
    void testBuyQuantityEqualSellQuantity() {
        final var buyRequest = generateBuyRequest();
        Mockito.when(this.sellRequestWaitingCandidateDataService.allocateSellRequests(buyRequest)).thenReturn(List.of(generateSellRequest(7)));

        tradeService.trade(buyRequest);
        Mockito.verify(tradeTransactionDataService).createTradeTransaction(Mockito.any());
        Mockito.verify(customerTradeTransactionDataService).createCustomerTradeTransaction(Mockito.any());

    }

    @Test
    void testBuyQuantityEqualSellQuantityWith2SellRequests() {
        final var buyRequest = generateBuyRequest();
        Mockito.when(this.sellRequestWaitingCandidateDataService.allocateSellRequests(buyRequest)).thenReturn(List.of(generateSellRequest(3), generateSellRequest(4)));

        tradeService.trade(buyRequest);
        Mockito.verify(tradeTransactionDataService, Mockito.times(2)).createTradeTransaction(Mockito.any());
        Mockito.verify(customerTradeTransactionDataService, Mockito.times(2)).createCustomerTradeTransaction(Mockito.any());
    }

    @Test
    void testBuyQuantityLessThanSellQuantity() {
        final var buyRequest = generateBuyRequest();
        final var sellRequest1 = generateSellRequest(4);
        final var sellRequest2 = generateSellRequest(4);
        Mockito.when(this.sellRequestWaitingCandidateDataService.allocateSellRequests(buyRequest)).thenReturn(List.of(sellRequest1, sellRequest2));

        tradeService.trade(buyRequest);
        Mockito.verify(tradeTransactionDataService, Mockito.times(2)).createTradeTransaction(Mockito.any());
        Mockito.verify(customerTradeTransactionDataService, Mockito.times(2)).createCustomerTradeTransaction(Mockito.any());
        sellRequest2.setRequestQuantity(1);
        Mockito.verify(sellRequestWaitingCandidateDataService).add(sellRequest2);
    }

    @Test
    void testBuyQuantityGreaterThanSellQuantity() {
        final var buyRequest = generateBuyRequest();
        final var sellRequest1 = generateSellRequest(2);
        final var sellRequest2 = generateSellRequest(2);
        Mockito.when(this.sellRequestWaitingCandidateDataService.allocateSellRequests(buyRequest)).thenReturn(List.of(sellRequest1, sellRequest2));

        tradeService.trade(buyRequest);
        Mockito.verify(tradeTransactionDataService, Mockito.times(2)).createTradeTransaction(Mockito.any());
        Mockito.verify(customerTradeTransactionDataService, Mockito.times(2)).createCustomerTradeTransaction(Mockito.any());
        final var buyRequestInQueue = inMemoryMessageQueue.pollBuyRequestInQueue();
        if (buyRequestInQueue.isPresent()) {
            Assertions.assertEquals(BUY_REQUEST_ID, buyRequestInQueue.get().getRequestId());
            Assertions.assertEquals(3, buyRequestInQueue.get().getRequestQuantity());
        } else {
            Assertions.fail("A BuyRequest should be in the queue");
        }

    }

    BuyRequest generateBuyRequest() {
        return BuyRequest.builder()
                .requestId(BUY_REQUEST_ID)
                .productId(PRODUCT_ID)
                .requestAmount(REQUEST_AMOUNT)
                .requestQuantity(REQUEST_QUANTITY)
                .requestTime(REQUEST_TIME)
                .customerId(CUSTOMER_ID)
                .build();
    }

    SellRequest generateSellRequest(int quantity) {
        return SellRequest.builder()
                .requestId(SELL_REQUEST_ID)
                .productId(PRODUCT_ID)
                .requestAmount(REQUEST_AMOUNT)
                .requestQuantity(quantity)
                .requestTime(REQUEST_TIME)
                .customerId(CUSTOMER_ID)
                .build();
    }
}