package com.zerologix.interview.tradeengine.trade.service;

import com.zerologix.interview.tradeengine.messagequeue.service.MessageQueuePublishService;
import com.zerologix.interview.tradeengine.trade.data.dataservice.SellRequestWaitingCandidateDataService;
import com.zerologix.interview.tradeengine.trade.data.dataservice.TradeTransactionDataService;
import com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest;
import com.zerologix.interview.tradeengine.trade.service.dto.SellRequest;
import com.zerologix.interview.tradeengine.trade.service.dto.TradeTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TradeServiceImpl implements TradeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TradeServiceImpl.class);
    private final SellRequestWaitingCandidateDataService sellRequestWaitingCandidateDataService;
    private final TradeTransactionDataService tradeTransactionDataService;
    private final MessageQueuePublishService messageQueuePublishService;

    @Autowired
    public TradeServiceImpl(final SellRequestWaitingCandidateDataService sellRequestWaitingCandidateDataService, final TradeTransactionDataService tradeTransactionDataService, final MessageQueuePublishService messageQueuePublishService) {
        this.sellRequestWaitingCandidateDataService = sellRequestWaitingCandidateDataService;
        this.tradeTransactionDataService = tradeTransactionDataService;
        this.messageQueuePublishService = messageQueuePublishService;
    }

    public void trade(final BuyRequest buyRequest) {
        if (!isValid(buyRequest)) {
            return;
        }

        final List<SellRequest> sellRequestList;
        try {
            sellRequestList = this.sellRequestWaitingCandidateDataService.allocateSellRequests(buyRequest);
        } catch (Exception e) {
            LOGGER.error("It's failed to allocate sell requests. Add the BuyRequest back in message queue again.", e);
            messageQueuePublishService.publish(buyRequest);
            return;
        }

        if (CollectionUtils.isEmpty(sellRequestList)) {
            messageQueuePublishService.publish(buyRequest);
        }
        int requestQuantity = buyRequest.getRequestQuantity();
        for (var sellRequest : sellRequestList) {
            if (requestQuantity <= 0) {
                break;
            }
            final var tradeTransaction = TradeTransaction.builder()
                    .transactionId(UUID.randomUUID().toString())
                    .productId(sellRequest.getProductId())
                    .amount(sellRequest.getRequestAmount())
                    .createdTime(new Date())
                    .quantity(Math.min(sellRequest.getRequestQuantity(), requestQuantity))
                    .buyRequest(buyRequest)
                    .sellRequest(sellRequest)
                    .build();

            if (sellRequest.getRequestQuantity() > tradeTransaction.getQuantity()) {
                sellRequest.setRequestQuantity(sellRequest.getRequestQuantity() - tradeTransaction.getQuantity());
                this.sellRequestWaitingCandidateDataService.add(sellRequest);
            }
            tradeTransactionDataService.createTradeTransaction(tradeTransaction);
            requestQuantity -= tradeTransaction.getQuantity();
        }

        if (requestQuantity > 0) {
            buyRequest.setRequestQuantity(requestQuantity);
            messageQueuePublishService.publish(buyRequest);
        }
    }

    private boolean isValid(final BuyRequest buyRequest) {
        final var dayBeforeNow = Calendar.getInstance();
        dayBeforeNow.add(Calendar.DATE, -1);

        final var requestDate = Calendar.getInstance();
        requestDate.setTime(buyRequest.getRequestTime());
        return requestDate.after(dayBeforeNow);
    }
}
