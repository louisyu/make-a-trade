package com.zerologix.interview.tradeengine.trade.service;

import com.zerologix.interview.tradeengine.trade.data.dataservice.SellRequestDataService;
import com.zerologix.interview.tradeengine.trade.data.dataservice.SellRequestWaitingCandidateDataService;
import com.zerologix.interview.tradeengine.trade.service.dto.SellRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * {@inheritDoc}
 */
@Service
public class SellRequestServiceImpl implements SellRequestService {
    private final SellRequestDataService sellRequestDataService;
    private final SellRequestWaitingCandidateDataService sellRequestWaitingCandidateDataService;

    @Autowired
    public SellRequestServiceImpl(final SellRequestDataService sellRequestDataService, final SellRequestWaitingCandidateDataService sellRequestWaitingCandidateDataService) {
        this.sellRequestDataService = sellRequestDataService;
        this.sellRequestWaitingCandidateDataService = sellRequestWaitingCandidateDataService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SellRequest createSellRequest(final SellRequest sellRequest) {
        final var createdSellRequest = sellRequestDataService.createSellRequest(sellRequest);
        sellRequestWaitingCandidateDataService.add(createdSellRequest);
        return createdSellRequest;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<SellRequest> findSellRequest(final String sellRequestId) {
        return sellRequestDataService.findSellRequest(sellRequestId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteSellRequest(final String sellRequestId) {
        final var sellRequestOptional = sellRequestDataService.findSellRequest(sellRequestId);
        if (sellRequestOptional.isPresent()) {
            final var sellRequest = sellRequestOptional.get();
            sellRequestDataService.deleteSellRequest(sellRequest);
            sellRequestWaitingCandidateDataService.remove(sellRequest);
            return true;
        } else {
            return false;
        }
    }
}
