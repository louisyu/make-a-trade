package com.zerologix.interview.tradeengine.trade.data.dataservice;

import com.zerologix.interview.tradeengine.trade.data.repository.SellRequestWaitingCandidateRepository;
import com.zerologix.interview.tradeengine.trade.data.transform.SellRequestDtoTransform;
import com.zerologix.interview.tradeengine.trade.data.transform.SellRequestWaitingCandidateDaoTransform;
import com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest;
import com.zerologix.interview.tradeengine.trade.service.dto.SellRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class SellRequestWaitingCandidateDataServiceImplTest {
    private static final String REQUEST_ID = "testRequestId";
    private static final String PRODUCT_ID = "testProductId";
    private static final String BUY_REQUEST_ID = "testBuyRequestId";
    private static final String SELL_REQUEST_ID = "testSellRequestId";
    private static final String BUY_CUSTOMER_ID = "testBuyCustomerId";
    private static final String SELL_CUSTOMER_ID = "testSellCustomerId";

    private static final BigDecimal REQUEST_AMOUNT = new BigDecimal("3.14");
    private static final int REQUEST_QUANTITY = 7;
    private static final Date REQUEST_TIME = new Date();

    private SellRequestWaitingCandidateDataService sellRequestWaitingCandidateDataService;
    @Mock
    private SellRequestWaitingCandidateRepository sellRequestWaitingCandidateRepository;

    private SellRequestWaitingCandidateDaoTransform sellRequestWaitingCandidateDaoTransform;

    @BeforeEach
    void setUp() {
        final var sellRequestDtoTransform = new SellRequestDtoTransform();
        sellRequestWaitingCandidateDaoTransform = new SellRequestWaitingCandidateDaoTransform();
        sellRequestWaitingCandidateDataService = new SellRequestWaitingCandidateDataServiceImpl(sellRequestWaitingCandidateRepository, sellRequestDtoTransform, sellRequestWaitingCandidateDaoTransform);
    }

    @Test
    void testAllocateSellRequests_NoSellRequest() {
        final var pageable = PageRequest.of(0, REQUEST_QUANTITY);
        Mockito.when(sellRequestWaitingCandidateRepository.findByProductIdAndAmountOrderByRequestTimeAsc(PRODUCT_ID, REQUEST_AMOUNT, pageable)).thenReturn(Collections.emptyList());
        final var sellRequestList = sellRequestWaitingCandidateDataService.allocateSellRequests(generateBuyRequest());
        Assertions.assertTrue(CollectionUtils.isEmpty(sellRequestList));
    }

    @Test
    void testAllocateSellRequests() {
        final var sellRequest = generateSellRequest();
        final var sellRequestWaitingCandidate = sellRequestWaitingCandidateDaoTransform.transform(sellRequest);
        final var pageable = PageRequest.of(0, REQUEST_QUANTITY);
        Mockito.when(sellRequestWaitingCandidateRepository.findByProductIdAndAmountOrderByRequestTimeAsc(PRODUCT_ID, REQUEST_AMOUNT, pageable)).thenReturn(List.of(sellRequestWaitingCandidate));
        final var sellRequestList = sellRequestWaitingCandidateDataService.allocateSellRequests(generateBuyRequest());
        Mockito.verify(sellRequestWaitingCandidateRepository).findByProductIdAndAmountOrderByRequestTimeAsc(PRODUCT_ID, REQUEST_AMOUNT, pageable);
        Mockito.verify(sellRequestWaitingCandidateRepository).delete(sellRequestWaitingCandidate);
        Assertions.assertFalse(CollectionUtils.isEmpty(sellRequestList));
        final var sellRequestInList = sellRequestList.get(0);
        Assertions.assertEquals(SELL_REQUEST_ID, sellRequestInList.getRequestId());
        Assertions.assertEquals(PRODUCT_ID, sellRequestInList.getProductId());
        Assertions.assertEquals(REQUEST_AMOUNT, sellRequestInList.getRequestAmount());
        Assertions.assertEquals(REQUEST_QUANTITY, sellRequestInList.getRequestQuantity());
        Assertions.assertEquals(REQUEST_TIME, sellRequestInList.getRequestTime());
        Assertions.assertEquals(SELL_CUSTOMER_ID, sellRequestInList.getCustomerId());
    }

    @Test
    void testAdd() {
        final var sellRequest = generateSellRequest();
        sellRequestWaitingCandidateDataService.add(sellRequest);
        Mockito.verify(sellRequestWaitingCandidateRepository).save(sellRequestWaitingCandidateDaoTransform.transform(sellRequest));
    }

    @Test
    void testRemove() {
        final var sellRequest = generateSellRequest();
        sellRequestWaitingCandidateDataService.remove(sellRequest);
        Mockito.verify(sellRequestWaitingCandidateRepository).delete(sellRequestWaitingCandidateDaoTransform.transform(sellRequest));
    }

    private BuyRequest generateBuyRequest() {
        return BuyRequest.builder()
                .requestId(BUY_REQUEST_ID)
                .productId(PRODUCT_ID)
                .requestAmount(REQUEST_AMOUNT)
                .requestQuantity(REQUEST_QUANTITY)
                .requestTime(REQUEST_TIME)
                .customerId(BUY_CUSTOMER_ID)
                .build();
    }

    private SellRequest generateSellRequest() {
        return SellRequest.builder()
                .requestId(SELL_REQUEST_ID)
                .productId(PRODUCT_ID)
                .requestAmount(REQUEST_AMOUNT)
                .requestQuantity(REQUEST_QUANTITY)
                .requestTime(REQUEST_TIME)
                .customerId(SELL_CUSTOMER_ID)
                .build();
    }
}