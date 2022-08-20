package com.zerologix.interview.tradeengine.trade.service;

import com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest;

public interface TradeService {
    void trade(BuyRequest buyRequest);
}
