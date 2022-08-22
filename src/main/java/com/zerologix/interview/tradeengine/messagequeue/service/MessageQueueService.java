package com.zerologix.interview.tradeengine.messagequeue.service;

import com.zerologix.interview.tradeengine.messagequeue.source.InMemoryMessageQueue;
import com.zerologix.interview.tradeengine.trade.service.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * MessageQueueService polls a buy request from the message queue and sends it to the trade service.
 */
@EnableAsync
@Service
public class MessageQueueService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageQueueService.class);
    private final TradeService tradeService;

    private final InMemoryMessageQueue inMemoryMessageQueue;

    @Autowired
    public MessageQueueService(final TradeService tradeService, final InMemoryMessageQueue inMemoryMessageQueue) {
        this.tradeService = tradeService;
        this.inMemoryMessageQueue = inMemoryMessageQueue;
    }

    /**
     * Polls a buy request from the message queue and send it to trade service to match the sell requests.
     */
    @Async
    @Scheduled(fixedRate = 10)
    public void checkBuyRequestInQueue() {
        LOGGER.info("Start to poll a buy request.");
        inMemoryMessageQueue.pollBuyRequestInQueue().ifPresent(tradeService::trade);
    }
}
