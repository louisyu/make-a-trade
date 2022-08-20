package com.zerologix.interview.tradeengine.trade.service;

import com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest;

import java.util.Optional;

public interface BuyRequestService {
    BuyRequest createBuyRequest(BuyRequest buyRequest);

    Optional<BuyRequest> findBuyRequest(String id);

    boolean deleteBuyRequest(String id);
}
