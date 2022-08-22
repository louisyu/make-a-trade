package com.zerologix.interview.tradeengine.trade.service;

import com.zerologix.interview.tradeengine.messagequeue.service.MessageQueuePublishService;
import com.zerologix.interview.tradeengine.trade.data.dataservice.BuyRequestDataService;
import com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * {@inheritDoc}
 */
@Service
public class BuyRequestServiceImpl implements BuyRequestService {
    private final BuyRequestDataService buyRequestDataService;
    private final MessageQueuePublishService messageQueuePublishService;

    @Autowired
    public BuyRequestServiceImpl(final BuyRequestDataService buyRequestDataService, final MessageQueuePublishService messageQueuePublishService) {
        this.buyRequestDataService = buyRequestDataService;
        this.messageQueuePublishService = messageQueuePublishService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuyRequest createBuyRequest(final BuyRequest buyRequest) {
        final var createdBuyRequest = buyRequestDataService.createBuyRequest(buyRequest);
        messageQueuePublishService.publish(buyRequest);
        return createdBuyRequest;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<BuyRequest> findBuyRequest(final String id) {
        return buyRequestDataService.findBuyRequest(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteBuyRequest(final String id) {
        final var buyRequestOptional = buyRequestDataService.findBuyRequest(id);
        if (buyRequestOptional.isPresent()) {
            final var buyRequest = buyRequestOptional.get();
            buyRequestDataService.deleteBuyRequest(buyRequest);
            messageQueuePublishService.remove(buyRequest);
            return true;
        } else {
            return false;
        }
    }
}
