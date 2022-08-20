package com.zerologix.interview.tradeengine.trade.data.dataservice;


import com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest;
import com.zerologix.interview.tradeengine.trade.service.dto.SellRequest;

import java.util.List;

public interface SellRequestWaitingCandidateDataService {
    List<SellRequest> allocateSellRequests(BuyRequest buyRequest);
    void add(SellRequest sellRequest);
    void remove(SellRequest sellRequest);
}
