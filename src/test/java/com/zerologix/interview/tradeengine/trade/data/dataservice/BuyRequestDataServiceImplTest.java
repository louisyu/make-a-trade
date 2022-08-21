package com.zerologix.interview.tradeengine.trade.data.dataservice;

import com.zerologix.interview.tradeengine.trade.data.repository.BuyRequestRepository;
import com.zerologix.interview.tradeengine.trade.data.transform.BuyRequestDaoTransform;
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
class BuyRequestDataServiceImplTest {
    private static final String REQUEST_ID = "testRequestId";
    private static final String PRODUCT_ID = "testProductId";
    private static final String CUSTOMER_ID = "testCustomerId";
    private static final BigDecimal REQUEST_AMOUNT = new BigDecimal("3.14");
    private static final int REQUEST_QUANTITY = 7;
    private static final Date REQUEST_TIME = new Date();

    private BuyRequestDataService buyRequestDataService;

    @Mock
    private BuyRequestRepository buyRequestRepository;

    private BuyRequestDaoTransform buyRequestDaoTransform;

    @BeforeEach
    void setUp() {
        buyRequestDaoTransform = new BuyRequestDaoTransform();
        buyRequestDataService = new BuyRequestDataServiceImpl(buyRequestRepository, buyRequestDaoTransform);
    }

    @Test
    void testFindBuyRequest() {
        final var buyRequest = generateBuyRequest();
        Mockito.when(buyRequestRepository.findById(REQUEST_ID)).thenReturn(Optional.of(buyRequestDaoTransform.transform(buyRequest)));
        final var buyRequestInDB = buyRequestDataService.findBuyRequest(REQUEST_ID);
        if(buyRequestInDB.isPresent()){
            Assertions.assertEquals(REQUEST_ID, buyRequestInDB.get().getRequestId());
            Assertions.assertEquals(PRODUCT_ID, buyRequestInDB.get().getProductId());
            Assertions.assertEquals(REQUEST_AMOUNT, buyRequestInDB.get().getRequestAmount());
            Assertions.assertEquals(REQUEST_QUANTITY, buyRequestInDB.get().getRequestQuantity());
            Assertions.assertEquals(REQUEST_TIME, buyRequestInDB.get().getRequestTime());
            Assertions.assertEquals(CUSTOMER_ID, buyRequestInDB.get().getCustomerId());
        }else{
            Assertions.fail("It should have a BuyRequest.");
        }
    }

    @Test
    void testFindNoBuyRequest() {
        Mockito.when(buyRequestRepository.findById(REQUEST_ID)).thenReturn(Optional.empty());
        Assertions.assertTrue(buyRequestDataService.findBuyRequest(REQUEST_ID).isEmpty());
    }

    @Test
    void createBuyRequest() {
        final var buyRequest = generateBuyRequest();
        final var buyRequestDao = buyRequestDaoTransform.transform(buyRequest);
        Mockito.when(buyRequestRepository.save(buyRequestDao)).thenReturn(buyRequestDao);
        final var buyRequestInDB = buyRequestDataService.createBuyRequest(buyRequest);
        Assertions.assertEquals(REQUEST_ID, buyRequestInDB.getRequestId());
        Assertions.assertEquals(PRODUCT_ID, buyRequestInDB.getProductId());
        Assertions.assertEquals(REQUEST_AMOUNT, buyRequestInDB.getRequestAmount());
        Assertions.assertEquals(REQUEST_QUANTITY, buyRequestInDB.getRequestQuantity());
        Assertions.assertEquals(REQUEST_TIME, buyRequestInDB.getRequestTime());
        Assertions.assertEquals(CUSTOMER_ID, buyRequestInDB.getCustomerId());
        Mockito.verify(buyRequestRepository).save(buyRequestDao);
    }

    @Test
    void deleteBuyRequest() {
        final var buyRequest = generateBuyRequest();
        final var buyRequestDao = buyRequestDaoTransform.transform(buyRequest);
        Mockito.when(buyRequestRepository.findById(REQUEST_ID)).thenReturn(Optional.ofNullable(buyRequestDao));
        buyRequestDataService.deleteBuyRequest(buyRequest);
        Mockito.verify(buyRequestRepository).delete(buyRequestDao);
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