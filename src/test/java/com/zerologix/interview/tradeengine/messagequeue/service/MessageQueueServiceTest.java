package com.zerologix.interview.tradeengine.messagequeue.service;

import com.zerologix.interview.tradeengine.messagequeue.source.InMemoryMessageQueue;
import com.zerologix.interview.tradeengine.trade.service.TradeService;
import com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

@ExtendWith(MockitoExtension.class)
class MessageQueueServiceTest {
    private static final BuyRequest BUY_REQUEST1 = BuyRequest.builder().requestId("testBuyRequestId1").build();
    private static final BuyRequest BUY_REQUEST2 = BuyRequest.builder().requestId("testBuyRequestId2").build();

    private ConcurrentLinkedQueue<BuyRequest> queue;

    private InMemoryMessageQueue inMemoryMessageQueue;

    private MessageQueueService messageQueueService;

    @Mock
    private TradeService tradeService;

    @BeforeEach
    void setUp() {
        queue = new ConcurrentLinkedQueue<>();
        this.inMemoryMessageQueue = new InMemoryMessageQueue(){
            @Override
            public void addBuyRequestInQueue(final BuyRequest buyRequest) {
                queue.add(buyRequest);
            }

            @Override
            public Optional<BuyRequest> pollBuyRequestInQueue() {
                return Optional.ofNullable(queue.poll());
            }

            @Override
            public void removeBuyRequestInQueue(final BuyRequest buyRequest) {
                queue.remove(buyRequest);
            }
        };

        this.messageQueueService = new MessageQueueService(tradeService, inMemoryMessageQueue);
    }

    @Test
    void checkBuyRequestInQueue() {
        this.inMemoryMessageQueue.addBuyRequestInQueue(BUY_REQUEST1);
        this.inMemoryMessageQueue.addBuyRequestInQueue(BUY_REQUEST2);

        this.messageQueueService.checkBuyRequestInQueue();
        Mockito.verify(this.tradeService).trade(BUY_REQUEST1);
        Mockito.verify(this.tradeService, Mockito.never()).trade(BUY_REQUEST2);

        this.messageQueueService.checkBuyRequestInQueue();
        Mockito.verify(this.tradeService).trade(BUY_REQUEST2);
    }
}