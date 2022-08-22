package com.zerologix.interview.tradeengine.messagequeue.service;

import com.zerologix.interview.tradeengine.messagequeue.source.InMemoryMessageQueue;
import com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * MessageQueuePublishServiceImpl connects to in-memory message queue.
 */
@Service
public class MessageQueuePublishServiceImpl implements MessageQueuePublishService {

    private final InMemoryMessageQueue inMemoryMessageQueue;

    @Autowired
    public MessageQueuePublishServiceImpl(final InMemoryMessageQueue inMemoryMessageQueue) {
        this.inMemoryMessageQueue = inMemoryMessageQueue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void publish(final BuyRequest buyRequest) {
        inMemoryMessageQueue.addBuyRequestInQueue(buyRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(final BuyRequest buyRequest) {
        inMemoryMessageQueue.removeBuyRequestInQueue(buyRequest);
    }
}
