package com.zerologix.interview.tradeengine.trade.data.dataservice;


import com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest;
import com.zerologix.interview.tradeengine.trade.service.dto.SellRequest;

import java.util.List;

/**
 * The service provide to manipulate SellRequestWaitingCandidate to access Cassandra DB.
 */
public interface SellRequestWaitingCandidateDataService {

    /**
     * Gets the product id, the request amount, and the request quantity from the given BuyRequest.
     * Uses them as the search criteria to find a list of sell requests.
     * Then, removes those data from SellRequestWaitingCandidate.
     *
     * @param buyRequest the buy request.
     * @return a list of sell requests
     */
    List<SellRequest> allocateSellRequests(BuyRequest buyRequest);

    /**
     * Transforms the SellRequest to SellRequestWaitingCandidate and stores it.
     *
     * @param sellRequest the sell request
     */
    void add(SellRequest sellRequest);

    /**
     * Removes the SellRequestWaitingCandidate by the given SellRequest.
     *
     * @param sellRequest the sell request
     */
    void remove(SellRequest sellRequest);
}
