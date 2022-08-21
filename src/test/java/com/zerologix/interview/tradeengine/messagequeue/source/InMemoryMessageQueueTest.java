package com.zerologix.interview.tradeengine.messagequeue.source;

import com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryMessageQueueTest {

    private static final BuyRequest BUY_REQUEST1 = BuyRequest.builder().requestId("testBuyRequestId1").build();
    private static final BuyRequest BUY_REQUEST2 = BuyRequest.builder().requestId("testBuyRequestId2").build();

    private InMemoryMessageQueue inMemoryMessageQueue;

    @BeforeEach
    void setUp() {
        this.inMemoryMessageQueue = new InMemoryMessageQueue();
    }

    @Test
    void testAddBuyRequestInQueue() {
        this.inMemoryMessageQueue.addBuyRequestInQueue(BUY_REQUEST1);
        this.inMemoryMessageQueue.addBuyRequestInQueue(BUY_REQUEST2);
        Assertions.assertEquals(BUY_REQUEST1, this.inMemoryMessageQueue.pollBuyRequestInQueue().orElse(null));
        Assertions.assertEquals(BUY_REQUEST2, this.inMemoryMessageQueue.pollBuyRequestInQueue().orElse(null));
    }

    @Test
    void testRemoveBuyRequestInQueue() {
        this.inMemoryMessageQueue.addBuyRequestInQueue(BUY_REQUEST1);
        this.inMemoryMessageQueue.addBuyRequestInQueue(BUY_REQUEST2);
        this.inMemoryMessageQueue.removeBuyRequestInQueue(BUY_REQUEST2);
        Assertions.assertEquals(BUY_REQUEST1, this.inMemoryMessageQueue.pollBuyRequestInQueue().get());
        Assertions.assertTrue(this.inMemoryMessageQueue.pollBuyRequestInQueue().isEmpty());
    }
}