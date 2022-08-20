package com.zerologix.interview.tradeengine.trade.data.transform;

import org.springframework.stereotype.Component;

@Component
public class BuyRequestDaoTransform {

    public com.zerologix.interview.tradeengine.trade.data.dao.BuyRequest transform(final com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest target) {
        return com.zerologix.interview.tradeengine.trade.data.dao.BuyRequest.builder()
                .requestId(target.getRequestId())
                .productId(target.getProductId())
                .customerId(target.getCustomerId())
                .requestAmount(target.getRequestAmount())
                .requestTime(target.getRequestTime())
                .requestQuantity(target.getRequestQuantity())
                .build();
    }

    public com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest transform(final com.zerologix.interview.tradeengine.trade.data.dao.BuyRequest target) {
        return com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest.builder()
                .requestId(target.getRequestId())
                .productId(target.getProductId())
                .customerId(target.getCustomerId())
                .requestAmount(target.getRequestAmount())
                .requestTime(target.getRequestTime())
                .requestQuantity(target.getRequestQuantity())
                .build();
    }
}
