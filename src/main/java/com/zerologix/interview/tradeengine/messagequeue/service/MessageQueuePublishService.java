package com.zerologix.interview.tradeengine.messagequeue.service;

import com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest;

/**
 * The interface MessageQueuePublishService defines methods;
 * 1. publishes a message to the message queue.
 * 2. removes a message from the message queue.
 */
public interface MessageQueuePublishService {

    /**
     * Publishes a message to the message queue.
     *
     * @param buyRequest a buy request
     */
    void publish(BuyRequest buyRequest);

    /**
     * Removes a message from the message queue.
     *
     * @param buyRequest a buy request
     */
    void remove(BuyRequest buyRequest);
}
