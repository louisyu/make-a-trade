package com.zerologix.interview.tradeengine.messagequeue.source;

import com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class InMemoryMessageQueue {
    private static final ConcurrentLinkedQueue<BuyRequest> QUEUE_IN_MEMORY = new ConcurrentLinkedQueue<>();

    public void addBuyRequestInQueue(final BuyRequest buyRequest) {
        QUEUE_IN_MEMORY.add(buyRequest);
    }

    public Optional<BuyRequest> pollBuyRequestInQueue() {
        return Optional.ofNullable(QUEUE_IN_MEMORY.poll());
    }

    public void removeBuyRequestInQueue(final BuyRequest buyRequest) {
        QUEUE_IN_MEMORY.remove(buyRequest);
    }
}
