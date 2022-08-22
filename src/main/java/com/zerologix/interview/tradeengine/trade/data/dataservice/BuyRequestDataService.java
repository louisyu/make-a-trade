package com.zerologix.interview.tradeengine.trade.data.dataservice;


import com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest;

import java.util.Optional;

/**
 * The service provide to manipulate BuyRequest to access Cassandra DB.
 */
public interface BuyRequestDataService {

    /**
     * Finds a buy request by the give request id.
     *
     * @param requestId the buy request id
     * @return an optional of BuyRequest
     */
    Optional<BuyRequest> findBuyRequest(String requestId);

    /**
     * Creates and stored a buy request into the Cassandra DB.
     *
     * @param buyRequest the buy request
     * @return a buy request
     */
    BuyRequest createBuyRequest(BuyRequest buyRequest);

    /**
     * Deletes a buy request from the cassandra DB.
     *
     * @param buyRequest the buy request
     */
    void deleteBuyRequest(BuyRequest buyRequest);
}
