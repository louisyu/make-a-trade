package com.zerologix.interview.tradeengine.trade.web.controller;

import com.zerologix.interview.tradeengine.trade.service.TradeTransactionService;
import com.zerologix.interview.tradeengine.trade.web.dto.ErrorMessage;
import com.zerologix.interview.tradeengine.trade.web.dto.TradeTransactionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * CustomerTradeTransactionController exposes the Customer Trade Transaction API to the clients.
 */
@RestController("/customer")
public class CustomerTradeTransactionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerTradeTransactionController.class);
    private final TradeTransactionService tradeTransactionService;

    @Autowired
    public CustomerTradeTransactionController(final TradeTransactionService tradeTransactionService) {
        this.tradeTransactionService = tradeTransactionService;
    }


    @GetMapping("/{customerId}/transactions")
    public ResponseEntity<Object> findTradeTransactionsByCustomerId(@PathVariable final String customerId) {
        try {
            final var result = tradeTransactionService.findTradeTransactionByCustomerId(customerId)
                    .stream()
                    .map(tradeTransaction ->
                            TradeTransactionResponse.builder()
                                    .transactionId(tradeTransaction.getTransactionId())
                                    .productId(tradeTransaction.getProductId())
                                    .amount(tradeTransaction.getAmount())
                                    .quantity(tradeTransaction.getQuantity())
                                    .createdTime(tradeTransaction.getCreatedTime())
                                    .build()
                    )
                    .toList();
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        } catch (Exception e) {
            LOGGER.error("Cannot find a list of trade transaction", e);
            return new ResponseEntity<>(new ErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
