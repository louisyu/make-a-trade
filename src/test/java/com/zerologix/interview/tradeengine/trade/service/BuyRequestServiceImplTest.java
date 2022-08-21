package com.zerologix.interview.tradeengine.trade.service;

import com.zerologix.interview.tradeengine.messagequeue.service.MessageQueuePublishService;
import com.zerologix.interview.tradeengine.trade.data.dataservice.BuyRequestDataService;
import com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BuyRequestServiceImplTest {
    private static final String REQUEST_ID = "testRequestId";
    private static final String PRODUCT_ID = "testProductId";
    private static final String CUSTOMER_ID = "testCustomerId";
    private static final BigDecimal REQUEST_AMOUNT = new BigDecimal("3.14");
    private static final int REQUEST_QUANTITY = 7;
    private static final Date REQUEST_TIME = new Date();

    private BuyRequestService buyRequestService;
    @Mock
    private BuyRequestDataService buyRequestDataService;

    @Mock
    private MessageQueuePublishService messageQueuePublishService;

    @BeforeEach
    void setUp() {
        buyRequestService = new BuyRequestServiceImpl(buyRequestDataService, messageQueuePublishService);
    }

    @Test
    void testCreateBuyRequest() {
        final var buyRequest = generateBuyRequest();
        Mockito.when(buyRequestDataService.createBuyRequest(buyRequest)).thenReturn(buyRequest);
        buyRequestService.createBuyRequest(buyRequest);
        Mockito.verify(buyRequestDataService).createBuyRequest(buyRequest);
        Mockito.verify(messageQueuePublishService).publish(buyRequest);
    }

    @Test
    void testFindBuyRequest() {
        final var buyRequest = generateBuyRequest();
        Mockito.when(buyRequestDataService.findBuyRequest(REQUEST_ID)).thenReturn(Optional.of(buyRequest));
        final var buyRequestInDB = buyRequestService.findBuyRequest(REQUEST_ID);
        if (buyRequestInDB.isPresent()) {
            Assertions.assertEquals(REQUEST_ID, buyRequestInDB.get().getRequestId());
            Assertions.assertEquals(PRODUCT_ID, buyRequestInDB.get().getProductId());
            Assertions.assertEquals(REQUEST_AMOUNT, buyRequestInDB.get().getRequestAmount());
            Assertions.assertEquals(REQUEST_QUANTITY, buyRequestInDB.get().getRequestQuantity());
            Assertions.assertEquals(REQUEST_TIME, buyRequestInDB.get().getRequestTime());
            Assertions.assertEquals(CUSTOMER_ID, buyRequestInDB.get().getCustomerId());
        } else {
            Assertions.fail("It should have a BuyRequest.");
        }
    }

    @Test
    void testDeleteBuyRequest() {
        final var buyRequest = generateBuyRequest();
        Mockito.when(buyRequestDataService.findBuyRequest(REQUEST_ID)).thenReturn(Optional.of(buyRequest));
        buyRequestService.deleteBuyRequest(REQUEST_ID);
        Mockito.verify(buyRequestDataService).deleteBuyRequest(buyRequest);
        Mockito.verify(messageQueuePublishService).remove(buyRequest);
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