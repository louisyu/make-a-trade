package com.zerologix.interview.tradeengine.trade.data.transform;

import com.zerologix.interview.tradeengine.trade.data.dao.SellRequestWaitingCandidate;
import com.zerologix.interview.tradeengine.trade.service.dto.SellRequest;
import org.springframework.stereotype.Component;

@Component
public class SellRequestWaitingCandidateDaoTransform {

    public SellRequestWaitingCandidate transform(final SellRequest sellRequest){
        return SellRequestWaitingCandidate.builder()
                .productId(sellRequest.getProductId())
                .amount(sellRequest.getRequestAmount())
                .quantity(sellRequest.getRequestQuantity())
                .requestTime(sellRequest.getRequestTime())
                .sellRequestId(sellRequest.getRequestId())
                .customerId(sellRequest.getCustomerId())
                .build();
    }
}
