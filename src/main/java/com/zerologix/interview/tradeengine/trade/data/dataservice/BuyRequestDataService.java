package com.zerologix.interview.tradeengine.trade.data.dataservice;


import com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest;

import java.util.Optional;

public interface BuyRequestDataService {
    Optional<BuyRequest> findBuyRequest(String id);

    BuyRequest createBuyRequest(BuyRequest buyRequest);
    void deleteBuyRequest(BuyRequest buyRequest);
}
