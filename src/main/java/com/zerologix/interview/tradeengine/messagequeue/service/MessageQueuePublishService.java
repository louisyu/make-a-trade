package com.zerologix.interview.tradeengine.messagequeue.service;

import com.zerologix.interview.tradeengine.messagequeue.source.InMemoryMessageQueue;
import com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageQueuePublishService {

    private final InMemoryMessageQueue inMemoryMessageQueue;

    @Autowired
    public MessageQueuePublishService(final InMemoryMessageQueue inMemoryMessageQueue) {
        this.inMemoryMessageQueue = inMemoryMessageQueue;
    }

    public void publish(final BuyRequest buyRequest) {
        inMemoryMessageQueue.addBuyRequestInQueue(buyRequest);
    }

    public void remove(final BuyRequest buyRequest) {
        inMemoryMessageQueue.removeBuyRequestInQueue(buyRequest);
    }
}
