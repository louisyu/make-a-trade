package com.zerologix.interview.tradeengine.trade.service;

import com.zerologix.interview.tradeengine.trade.service.dto.SellRequest;

import java.util.Optional;

public interface SellRequestService {
    SellRequest createSellRequest(SellRequest sellRequest);

    Optional<SellRequest> findSellRequest(String sellRequestId);

    boolean deleteSellRequest(String sellRequestId);
}
