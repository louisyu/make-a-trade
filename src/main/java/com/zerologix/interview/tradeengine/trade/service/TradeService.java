package com.zerologix.interview.tradeengine.trade.service;

import com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest;

/**
 * TradeService matches the buy requests and the sell requests with the same product id, request amount, and request quantity.
 * If successfully matching, a trade transaction will be created and stored in DB.
 */
public interface TradeService {

    /**
     * Matches the buy requests and the sell requests with the same product id, request amount, and request quantity.
     * If successfully matching, a trade transaction will be created and stored in DB.
     *
     * @param buyRequest the buy request
     */
    void trade(BuyRequest buyRequest);
}
