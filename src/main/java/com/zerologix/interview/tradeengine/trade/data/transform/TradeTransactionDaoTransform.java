package com.zerologix.interview.tradeengine.trade.data.transform;

import com.zerologix.interview.tradeengine.trade.data.dataservice.BuyRequestDataService;
import com.zerologix.interview.tradeengine.trade.data.dataservice.SellRequestDataService;
import com.zerologix.interview.tradeengine.trade.service.dto.TradeTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TradeTransactionDaoTransform {
    private final BuyRequestDataService buyRequestDataService;
    private final SellRequestDataService sellRequestDataService;

    @Autowired
    public TradeTransactionDaoTransform(final BuyRequestDataService buyRequestDataService, final SellRequestDataService sellRequestDataService) {
        this.buyRequestDataService = buyRequestDataService;
        this.sellRequestDataService = sellRequestDataService;
    }

    public com.zerologix.interview.tradeengine.trade.data.dao.TradeTransaction transform(final com.zerologix.interview.tradeengine.trade.service.dto.TradeTransaction tradeTransaction) {
        return com.zerologix.interview.tradeengine.trade.data.dao.TradeTransaction.builder()
                .transactionId(tradeTransaction.getTransactionId())
                .amount(tradeTransaction.getAmount())
                .productId(tradeTransaction.getProductId())
                .createdTime(tradeTransaction.getCreatedTime())
                .buyRequestId(tradeTransaction.getBuyRequest().getRequestId())
                .sellRequestId(tradeTransaction.getSellRequest().getRequestId())
                .quantity(tradeTransaction.getQuantity())
                .build();
    }

    public com.zerologix.interview.tradeengine.trade.service.dto.TradeTransaction transform(final com.zerologix.interview.tradeengine.trade.data.dao.TradeTransaction tradeTransaction) {
        return TradeTransaction.builder()
                .transactionId(tradeTransaction.getTransactionId())
                .amount(tradeTransaction.getAmount())
                .productId(tradeTransaction.getProductId())
                .createdTime(tradeTransaction.getCreatedTime())
                .buyRequest(buyRequestDataService.findBuyRequest(tradeTransaction.getBuyRequestId()).orElseThrow())
                .sellRequest(sellRequestDataService.findSellRequest(tradeTransaction.getSellRequestId()).orElseThrow())
                .quantity(tradeTransaction.getQuantity())
                .build();
    }
}
