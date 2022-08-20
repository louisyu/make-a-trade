package com.zerologix.interview.tradeengine.trade.data.dataservice;

import com.zerologix.interview.tradeengine.trade.service.dto.SellRequest;

import java.util.Optional;

public interface SellRequestDataService {
    Optional<SellRequest> findSellRequest(String id);

    SellRequest createSellRequest(SellRequest sellRequest);

    void deleteSellRequest(SellRequest sellRequest);
}
