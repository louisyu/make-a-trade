package com.zerologix.interview.tradeengine.trade.service;

import com.zerologix.interview.tradeengine.messagequeue.service.MessageQueuePublishService;
import com.zerologix.interview.tradeengine.trade.data.dataservice.CustomerTradeTransactionDataService;
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

/**
 * {@inheritDoc}
 */
@Service
public class TradeServiceImpl implements TradeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TradeServiceImpl.class);
    private final SellRequestWaitingCandidateDataService sellRequestWaitingCandidateDataService;
    private final TradeTransactionDataService tradeTransactionDataService;
    private final CustomerTradeTransactionDataService customerTradeTransactionDataService;
    private final MessageQueuePublishService messageQueuePublishService;

    @Autowired
    public TradeServiceImpl(final SellRequestWaitingCandidateDataService sellRequestWaitingCandidateDataService, final TradeTransactionDataService tradeTransactionDataService, final CustomerTradeTransactionDataService customerTradeTransactionDataService, final MessageQueuePublishService messageQueuePublishService) {
        this.sellRequestWaitingCandidateDataService = sellRequestWaitingCandidateDataService;
        this.tradeTransactionDataService = tradeTransactionDataService;
        this.customerTradeTransactionDataService = customerTradeTransactionDataService;
        this.messageQueuePublishService = messageQueuePublishService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
            LOGGER.info("There no matching sell request, so push the buy request back to the message queue waiting for the next trial");
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
            createTradeTransaction(tradeTransaction);
            LOGGER.info("Successfully make a trade and create a trade transaction.");
            requestQuantity -= tradeTransaction.getQuantity();
        }

        if (requestQuantity > 0) {
            buyRequest.setRequestQuantity(requestQuantity);
            messageQueuePublishService.publish(buyRequest);
            LOGGER.info("The buy request has remain quantity, so update the quantity and push it back to the message queue.");
        }
    }

    /**
     * Checks whether the buy request is valid by checking the request time.
     * Returns true if the request time is within one day.
     * Returns false if the request time is more than one day before.
     *
     * @param buyRequest the buy request
     * @return true if the request time is within one day.
     */
    private boolean isValid(final BuyRequest buyRequest) {
        final var dayBeforeNow = Calendar.getInstance();
        dayBeforeNow.add(Calendar.DATE, -1);

        final var requestDate = Calendar.getInstance();
        requestDate.setTime(buyRequest.getRequestTime());
        return requestDate.after(dayBeforeNow);
    }

    /**
     * Creates and stores the trade transaction to DB.
     *
     * @param tradeTransaction the trade transaction
     */
    private void createTradeTransaction(final TradeTransaction tradeTransaction) {
        tradeTransactionDataService.createTradeTransaction(tradeTransaction);
        customerTradeTransactionDataService.createCustomerTradeTransaction(tradeTransaction);
    }
}
