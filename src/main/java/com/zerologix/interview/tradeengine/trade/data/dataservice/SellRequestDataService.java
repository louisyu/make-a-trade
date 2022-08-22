package com.zerologix.interview.tradeengine.trade.data.dataservice;

import com.zerologix.interview.tradeengine.trade.service.dto.SellRequest;

import java.util.Optional;

/**
 * The service provide to manipulate SellRequest to access Cassandra DB.
 */
public interface SellRequestDataService {

    /**
     * Finds a sell request by the give request id.
     *
     * @param requestId the sell request id
     * @return an optional of SellRequest
     */
    Optional<SellRequest> findSellRequest(String requestId);

    /**
     * Creates and stored a sell request into the Cassandra DB.
     *
     * @param sellRequest the sell request
     * @return a sell request
     */
    SellRequest createSellRequest(SellRequest sellRequest);

    /**
     * Deletes a sell request from the cassandra DB.
     *
     * @param sellRequest the sell request
     */
    void deleteSellRequest(SellRequest sellRequest);
}
