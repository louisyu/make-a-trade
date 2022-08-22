package com.zerologix.interview.tradeengine.trade.web.controller;

import com.zerologix.interview.tradeengine.trade.service.BuyRequestService;
import com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest;
import com.zerologix.interview.tradeengine.trade.web.dto.BuySellRequest;
import com.zerologix.interview.tradeengine.trade.web.dto.BuySellResponse;
import com.zerologix.interview.tradeengine.trade.web.dto.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * BuyRequestController exposes the Buy Request API to the clients.
 */
@RestController("/buy/request")
public class BuyRequestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuyRequestController.class);
    private final BuyRequestService buyRequestService;

    @Autowired
    public BuyRequestController(final BuyRequestService buyRequestService) {
        this.buyRequestService = buyRequestService;
    }

    @PostMapping("/")
    public ResponseEntity<Object> createBuyRequest(@RequestBody final BuySellRequest buySellRequest) {
        final var buyRequestDTO = BuyRequest.builder()
                .productId(buySellRequest.getProductId())
                .requestAmount(buySellRequest.getRequestAmount())
                .requestQuantity(buySellRequest.getRequestQuantity())
                .requestTime(new Date())
                .customerId(buySellRequest.getCustomerId())
                .requestId(UUID.randomUUID().toString())
                .build();
        try {
            final var createdBuyRequest = this.buyRequestService.createBuyRequest(buyRequestDTO);
            final var buySellResponse = BuySellResponse.builder()
                    .requestId(createdBuyRequest.getRequestId())
                    .productId(createdBuyRequest.getProductId())
                    .requestAmount(createdBuyRequest.getRequestAmount())
                    .requestQuantity(createdBuyRequest.getRequestQuantity())
                    .requestTime(createdBuyRequest.getRequestTime())
                    .customerId(createdBuyRequest.getCustomerId())
                    .build();
            return new ResponseEntity<>(buySellResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Cannot create a buy request", e);
            return new ResponseEntity<>(new ErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{buyRequestId}")
    public ResponseEntity<Object> getBuyRequest(@PathVariable final String buyRequestId) {
        try {
            return this.buyRequestService.findBuyRequest(buyRequestId)
                    .map(buyRequest -> BuySellResponse.builder()
                            .requestId(buyRequest.getRequestId())
                            .productId(buyRequest.getProductId())
                            .requestQuantity(buyRequest.getRequestQuantity())
                            .requestAmount(buyRequest.getRequestAmount())
                            .requestTime(buyRequest.getRequestTime())
                            .customerId(buyRequest.getCustomerId())
                            .build())
                    .<ResponseEntity<Object>>map(buyRequest -> new ResponseEntity<>(buyRequest, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            LOGGER.error("Cannot get a buy request", e);
            return new ResponseEntity<>(new ErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{buyRequestId}")
    public ResponseEntity<Object> deleteBuyRequest(@PathVariable final String buyRequestId) {
        try {
            return this.buyRequestService.deleteBuyRequest(buyRequestId) ? new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpEntity.EMPTY, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            LOGGER.error("Cannot delete a buy request", e);
            return new ResponseEntity<>(new ErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
