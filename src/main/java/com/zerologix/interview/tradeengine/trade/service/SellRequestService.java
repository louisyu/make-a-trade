package com.zerologix.interview.tradeengine.trade.service;

import com.zerologix.interview.tradeengine.trade.service.dto.SellRequest;

import java.util.Optional;

/**
 * SellRequestService provides methods related to SellRequest.
 */
public interface SellRequestService {
    /**
     * Creates and stores a SellRequest.
     *
     * @param sellRequest the sell request
     * @return a SellRequest
     */
    SellRequest createSellRequest(SellRequest sellRequest);

    /**
     * Finds a SellRequest in the system.
     *
     * @param sellRequestId the request id
     * @return an optional of SellRequest
     */
    Optional<SellRequest> findSellRequest(String sellRequestId);

    /**
     * Deletes the SellRequest by the request id.
     *
     * @param sellRequestId the request id
     * @return true if delete successfully
     */
    boolean deleteSellRequest(String sellRequestId);
}
