package com.zerologix.interview.tradeengine.trade.data.dataservice;

import com.zerologix.interview.tradeengine.trade.data.repository.SellRequestRepository;
import com.zerologix.interview.tradeengine.trade.data.transform.SellRequestDaoTransform;
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
class SellRequestDataServiceImplTest {
    private static final String REQUEST_ID = "testRequestId";
    private static final String PRODUCT_ID = "testProductId";
    private static final String CUSTOMER_ID = "testCustomerId";
    private static final BigDecimal REQUEST_AMOUNT = new BigDecimal("3.14");
    private static final int REQUEST_QUANTITY = 7;
    private static final Date REQUEST_TIME = new Date();

    private SellRequestDataService sellRequestDataService;

    @Mock
    private SellRequestRepository sellRequestRepository;

    private SellRequestDaoTransform sellRequestDaoTransform;

    @BeforeEach
    void setUp() {
        sellRequestDaoTransform = new SellRequestDaoTransform();
        sellRequestDataService = new SellRequestDataServiceImpl(sellRequestRepository, sellRequestDaoTransform);
    }

    @Test
    void testFindSellRequest() {
        final var sellRequest = generateSellRequest();
        Mockito.when(sellRequestRepository.findById(REQUEST_ID)).thenReturn(Optional.of(sellRequestDaoTransform.transform(sellRequest)));
        final var sellRequestInDB = sellRequestDataService.findSellRequest(REQUEST_ID);
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
    void testFindNoSellRequest() {
        Mockito.when(sellRequestRepository.findById(REQUEST_ID)).thenReturn(Optional.empty());
        Assertions.assertTrue(sellRequestDataService.findSellRequest(REQUEST_ID).isEmpty());
    }

    @Test
    void createSellRequest() {
        final var sellRequest = generateSellRequest();
        final var sellRequestDao = sellRequestDaoTransform.transform(sellRequest);
        Mockito.when(sellRequestRepository.save(sellRequestDao)).thenReturn(sellRequestDao);
        final var sellRequestInDB = sellRequestDataService.createSellRequest(sellRequest);
        Assertions.assertEquals(REQUEST_ID, sellRequestInDB.getRequestId());
        Assertions.assertEquals(PRODUCT_ID, sellRequestInDB.getProductId());
        Assertions.assertEquals(REQUEST_AMOUNT, sellRequestInDB.getRequestAmount());
        Assertions.assertEquals(REQUEST_QUANTITY, sellRequestInDB.getRequestQuantity());
        Assertions.assertEquals(REQUEST_TIME, sellRequestInDB.getRequestTime());
        Assertions.assertEquals(CUSTOMER_ID, sellRequestInDB.getCustomerId());
        Mockito.verify(sellRequestRepository).save(sellRequestDao);
    }

    @Test
    void deleteSellRequest() {
        final var sellRequest = generateSellRequest();
        final var sellRequestDao = sellRequestDaoTransform.transform(sellRequest);
        Mockito.when(sellRequestRepository.findById(REQUEST_ID)).thenReturn(Optional.ofNullable(sellRequestDao));
        sellRequestDataService.deleteSellRequest(sellRequest);
        Mockito.verify(sellRequestRepository).delete(sellRequestDao);
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