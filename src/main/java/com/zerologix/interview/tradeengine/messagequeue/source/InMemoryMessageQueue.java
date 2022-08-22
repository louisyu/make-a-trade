package com.zerologix.interview.tradeengine.messagequeue.source;

import com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This is an in-memory message queue.
 */
@Service
public class InMemoryMessageQueue {
    private static final ConcurrentLinkedQueue<BuyRequest> QUEUE_IN_MEMORY = new ConcurrentLinkedQueue<>();

    /**
     * Adds a buy request into the queue.
     *
     * @param buyRequest the buy request
     */
    public void addBuyRequestInQueue(final BuyRequest buyRequest) {
        QUEUE_IN_MEMORY.add(buyRequest);
    }

    /**
     * Polls a buy request from the queue.
     *
     * @return an optional of a buy request
     */
    public Optional<BuyRequest> pollBuyRequestInQueue() {
        return Optional.ofNullable(QUEUE_IN_MEMORY.poll());
    }

    /**
     * Removes a buy request out of the queue.
     *
     * @param buyRequest the buy request
     */
    public void removeBuyRequestInQueue(final BuyRequest buyRequest) {
        QUEUE_IN_MEMORY.remove(buyRequest);
    }
}
