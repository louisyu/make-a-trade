package com.zerologix.interview.tradeengine.trade.service;

import com.zerologix.interview.tradeengine.trade.data.dataservice.SellRequestDataService;
import com.zerologix.interview.tradeengine.trade.data.dataservice.SellRequestWaitingCandidateDataService;
import com.zerologix.interview.tradeengine.trade.service.dto.SellRequest;
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
class SellRequestServiceImplTest {
    private static final String REQUEST_ID = "testRequestId";
    private static final String PRODUCT_ID = "testProductId";
    private static final String CUSTOMER_ID = "testCustomerId";
    private static final BigDecimal REQUEST_AMOUNT = new BigDecimal("3.14");
    private static final int REQUEST_QUANTITY = 7;
    private static final Date REQUEST_TIME = new Date();

    private SellRequestService sellRequestService;

    @Mock
    private SellRequestDataService sellRequestDataService;

    @Mock
    private SellRequestWaitingCandidateDataService sellRequestWaitingCandidateDataService;


    @BeforeEach
    void setUp() {
        sellRequestService = new SellRequestServiceImpl(sellRequestDataService, sellRequestWaitingCandidateDataService);
    }

    @Test
    void createSellRequest() {
        final var sellRequest = generateSellRequest();
        Mockito.when(sellRequestDataService.createSellRequest(sellRequest)).thenReturn(sellRequest);
        sellRequestService.createSellRequest(sellRequest);
        Mockito.verify(sellRequestDataService).createSellRequest(sellRequest);
        Mockito.verify(sellRequestWaitingCandidateDataService).add(sellRequest);
    }

    @Test
    void findSellRequest() {
        final var sellRequest = generateSellRequest();
        Mockito.when(sellRequestDataService.findSellRequest(REQUEST_ID)).thenReturn(Optional.of(sellRequest));
        final var sellRequestInDB = sellRequestService.findSellRequest(REQUEST_ID);
        if (sellRequestInDB.isPresent()) {
            Assertions.assertEquals(REQUEST_ID, sellRequestInDB.get().getRequestId());
            Assertions.assertEquals(PRODUCT_ID, sellRequestInDB.get().getProductId());
            Assertions.assertEquals(REQUEST_AMOUNT, sellRequestInDB.get().getRequestAmount());
            Assertions.assertEquals(REQUEST_QUANTITY, sellRequestInDB.get().getRequestQuantity());
            Assertions.assertEquals(REQUEST_TIME, sellRequestInDB.get().getRequestTime());
            Assertions.assertEquals(CUSTOMER_ID, sellRequestInDB.get().getCustomerId());
        } else {
            Assertions.fail("It should have a SellRequest.");
        }

    }

    @Test
    void deleteSellRequest() {
        final var sellRequest = generateSellRequest();
        Mockito.when(sellRequestDataService.findSellRequest(REQUEST_ID)).thenReturn(Optional.of(sellRequest));
        sellRequestService.deleteSellRequest(REQUEST_ID);
        Mockito.verify(sellRequestDataService).deleteSellRequest(sellRequest);
        Mockito.verify(sellRequestWaitingCandidateDataService).remove(sellRequest);
    }

    SellRequest generateSellRequest() {
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