package com.zerologix.interview.tradeengine.trade.web.controller;

import com.zerologix.interview.tradeengine.trade.service.SellRequestService;
import com.zerologix.interview.tradeengine.trade.service.dto.SellRequest;
import com.zerologix.interview.tradeengine.trade.web.dto.BuySellRequest;
import com.zerologix.interview.tradeengine.trade.web.dto.BuySellResponse;
import com.zerologix.interview.tradeengine.trade.web.dto.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

/**
 * SellRequestController exposes the Sell Request API to the clients.
 */
@RestController("/sell/request")
public class SellRequestController {
    private final SellRequestService sellRequestService;

    @Autowired
    public SellRequestController(final SellRequestService sellRequestService) {
        this.sellRequestService = sellRequestService;
    }

    @PostMapping("/")
    public ResponseEntity<Object> createSellRequest(@RequestBody final BuySellRequest buySellRequest) {
        final var sellRequestDTO = SellRequest.builder()
                .productId(buySellRequest.getProductId())
                .requestAmount(buySellRequest.getRequestAmount())
                .requestQuantity(buySellRequest.getRequestQuantity())
                .requestTime(new Date())
                .customerId(buySellRequest.getCustomerId())
                .requestId(UUID.randomUUID().toString())
                .build();
        try {
            final var createdSellRequest = this.sellRequestService.createSellRequest(sellRequestDTO);
            final var buySellResponse = BuySellResponse.builder()
                    .requestId(createdSellRequest.getRequestId())
                    .productId(createdSellRequest.getProductId())
                    .requestAmount(createdSellRequest.getRequestAmount())
                    .requestQuantity(createdSellRequest.getRequestQuantity())
                    .requestTime(createdSellRequest.getRequestTime())
                    .customerId(createdSellRequest.getCustomerId())
                    .build();
            return new ResponseEntity<>(buySellResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{sellRequestId}")
    public ResponseEntity<Object> getSellRequest(@PathVariable final String sellRequestId) {
        try {
            final var result = sellRequestService.findSellRequest(sellRequestId);
            return result.map(sellRequest -> BuySellResponse.builder()
                            .requestId(sellRequest.getRequestId())
                            .productId(sellRequest.getProductId())
                            .requestAmount(sellRequest.getRequestAmount())
                            .requestQuantity(sellRequest.getRequestQuantity())
                            .requestTime(sellRequest.getRequestTime())
                            .customerId(sellRequest.getCustomerId())
                            .build())
                    .<ResponseEntity<Object>>map(sellRequest -> new ResponseEntity<>(sellRequest, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{sellRequestId}")
    public ResponseEntity<Object> deleteSellRequest(@PathVariable final String sellRequestId) {
        try {
            return this.sellRequestService.deleteSellRequest(sellRequestId) ? new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
