package com.zerologix.interview.tradeengine.trade.service;

import com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest;

import java.util.Optional;

/**
 * BuyRequestService provides methods related to BuyRequest.
 */
public interface BuyRequestService {

    /**
     * Creates and stores a BuyRequest.
     *
     * @param buyRequest the buy request
     * @return a BuyRequest
     */
    BuyRequest createBuyRequest(BuyRequest buyRequest);

    /**
     * Finds a BuyRequest in the system.
     *
     * @param id the request id
     * @return an optional of BuyRequest
     */
    Optional<BuyRequest> findBuyRequest(String id);

    /**
     * Deletes the BuyRequest by the request id.
     *
     * @param id the request id
     * @return true if delete successfully
     */
    boolean deleteBuyRequest(String id);
}
