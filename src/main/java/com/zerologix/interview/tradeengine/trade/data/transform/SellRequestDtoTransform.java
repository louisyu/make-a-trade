package com.zerologix.interview.tradeengine.trade.data.transform;

import com.zerologix.interview.tradeengine.trade.data.dao.SellRequestWaitingCandidate;
import com.zerologix.interview.tradeengine.trade.service.dto.SellRequest;
import org.springframework.stereotype.Component;

@Component
public class SellRequestDtoTransform {
    public SellRequest transform(final SellRequestWaitingCandidate sellRequestWaitingCandidate) {
        return SellRequest.builder()
                .requestId(sellRequestWaitingCandidate.getSellRequestId())
                .requestQuantity(sellRequestWaitingCandidate.getQuantity())
                .requestAmount(sellRequestWaitingCandidate.getAmount())
                .requestTime(sellRequestWaitingCandidate.getRequestTime())
                .productId(sellRequestWaitingCandidate.getProductId())
                .customerId(sellRequestWaitingCandidate.getCustomerId())
                .build();
    }
}
