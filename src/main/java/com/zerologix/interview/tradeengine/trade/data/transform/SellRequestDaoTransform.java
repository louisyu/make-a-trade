package com.zerologix.interview.tradeengine.trade.data.transform;

import org.springframework.stereotype.Component;

@Component
public class SellRequestDaoTransform {

    public com.zerologix.interview.tradeengine.trade.data.dao.SellRequest transform(final com.zerologix.interview.tradeengine.trade.service.dto.SellRequest target) {
        return com.zerologix.interview.tradeengine.trade.data.dao.SellRequest.builder()
                .requestId(target.getRequestId())
                .productId(target.getProductId())
                .customerId(target.getCustomerId())
                .requestAmount(target.getRequestAmount())
                .requestTime(target.getRequestTime())
                .requestQuantity(target.getRequestQuantity())
                .build();
    }

    public com.zerologix.interview.tradeengine.trade.service.dto.SellRequest transform(final com.zerologix.interview.tradeengine.trade.data.dao.SellRequest target) {
        return com.zerologix.interview.tradeengine.trade.service.dto.SellRequest.builder()
                .requestId(target.getRequestId())
                .productId(target.getProductId())
                .customerId(target.getCustomerId())
                .requestAmount(target.getRequestAmount())
                .requestTime(target.getRequestTime())
                .requestQuantity(target.getRequestQuantity())
                .build();
    }
}
