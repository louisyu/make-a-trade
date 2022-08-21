package com.zerologix.interview.tradeengine.messagequeue.service;

import com.zerologix.interview.tradeengine.messagequeue.source.InMemoryMessageQueue;
import com.zerologix.interview.tradeengine.trade.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@EnableAsync
@Service
public class MessageQueueService {
    private final TradeService tradeService;

    private final InMemoryMessageQueue inMemoryMessageQueue;

    @Autowired
    public MessageQueueService(final TradeService tradeService, final InMemoryMessageQueue inMemoryMessageQueue) {
        this.tradeService = tradeService;
        this.inMemoryMessageQueue = inMemoryMessageQueue;
    }

    @Async
    @Scheduled(fixedRate = 10)
    public void checkBuyRequestInQueue() {
        inMemoryMessageQueue.pollBuyRequestInQueue().ifPresent(tradeService::trade);
    }
}
