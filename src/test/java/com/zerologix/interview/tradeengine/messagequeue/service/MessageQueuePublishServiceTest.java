package com.zerologix.interview.tradeengine.messagequeue.service;

import com.zerologix.interview.tradeengine.messagequeue.source.InMemoryMessageQueue;
import com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MessageQueuePublishServiceTest {

    private static final BuyRequest BUY_REQUEST1 = BuyRequest.builder().requestId("testBuyRequestId1").build();
    private static final BuyRequest BUY_REQUEST2 = BuyRequest.builder().requestId("testBuyRequestId2").build();

    @Mock
    private InMemoryMessageQueue inMemoryMessageQueue;

    private MessageQueuePublishService messageQueuePublishService;

    @BeforeEach
    void setUp() {
        this.messageQueuePublishService = new MessageQueuePublishServiceImpl(inMemoryMessageQueue);
    }

    @Test
    void testPublish() {
        this.messageQueuePublishService.publish(BUY_REQUEST1);
        Mockito.verify(this.inMemoryMessageQueue).addBuyRequestInQueue(BUY_REQUEST1);
        Mockito.verify(this.inMemoryMessageQueue, Mockito.never()).addBuyRequestInQueue(BUY_REQUEST2);
        this.messageQueuePublishService.publish(BUY_REQUEST2);
        Mockito.verify(this.inMemoryMessageQueue).addBuyRequestInQueue(BUY_REQUEST2);
    }

    @Test
    void testRemove() {
        this.messageQueuePublishService.remove(BUY_REQUEST1);
        Mockito.verify(this.inMemoryMessageQueue).removeBuyRequestInQueue(BUY_REQUEST1);
        Mockito.verify(this.inMemoryMessageQueue, Mockito.never()).removeBuyRequestInQueue(BUY_REQUEST2);
        this.messageQueuePublishService.remove(BUY_REQUEST2);
        Mockito.verify(this.inMemoryMessageQueue).removeBuyRequestInQueue(BUY_REQUEST2);
    }
}