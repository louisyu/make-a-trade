package com.zerologix.interview.tradeengine.trade.web.controller;

import com.zerologix.interview.tradeengine.trade.service.TradeTransactionService;
import com.zerologix.interview.tradeengine.trade.web.dto.ErrorMessage;
import com.zerologix.interview.tradeengine.trade.web.dto.TradeTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/transaction")
public class TradeTransactionController {
    private final TradeTransactionService tradeTransactionService;

    @Autowired
    public TradeTransactionController(final TradeTransactionService tradeTransactionService) {
        this.tradeTransactionService = tradeTransactionService;
    }


    @GetMapping("/{tradeTransactionId}")
    public ResponseEntity<Object> getTransactionId(@PathVariable final String tradeTransactionId) {
        try {
            final var result = tradeTransactionService.getTradeTransaction(tradeTransactionId)
                    .map(tradeTransaction ->
                            TradeTransaction.builder()
                                    .transactionId(tradeTransaction.getTransactionId())
                                    .productId(tradeTransaction.getProductId())
                                    .amount(tradeTransaction.getAmount())
                                    .quantity(tradeTransaction.getQuantity())
                                    .createdTime(tradeTransaction.getCreatedTime())
                                    .build()
                    );
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(result.get(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
