package com.zerologix.interview.tradeengine.trade.service.dto;

import java.math.BigDecimal;
import java.util.Date;

public class TradeTransaction {
    private String transactionId;
    private String productId;
    private BigDecimal amount;
    private int quantity;
    private Date createdTime;
    private SellRequest sellRequest;
    private BuyRequest buyRequest;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(final String transactionId) {
        this.transactionId = transactionId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(final String productId) {
        this.productId = productId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(final Date createdTime) {
        this.createdTime = createdTime;
    }

    public SellRequest getSellRequest() {
        return sellRequest;
    }

    public void setSellRequest(final SellRequest sellRequest) {
        this.sellRequest = sellRequest;
    }

    public BuyRequest getBuyRequest() {
        return buyRequest;
    }

    public void setBuyRequest(final BuyRequest buyRequest) {
        this.buyRequest = buyRequest;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private String transactionId;
        private String productId;
        private BigDecimal amount;
        private int quantity;
        private Date createdTime;
        private SellRequest sellRequest;
        private BuyRequest buyRequest;

        public Builder populateBuilder(final SellRequest sellRequest, final BuyRequest buyRequest, final int quantity){
            this.productId = sellRequest.getProductId();
            this.amount = sellRequest.getRequestAmount();
            this.quantity = quantity;
            this.createdTime = new Date();
            this.sellRequest = sellRequest;
            this.buyRequest = buyRequest;
            return this;
        }

        public Builder transactionId(final String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public Builder productId(final String productId) {
            this.productId = productId;
            return this;
        }

        public Builder amount(final BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder quantity(final int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder createdTime(final Date createdTime) {
            this.createdTime = createdTime;
            return this;
        }

        public Builder sellRequest(final SellRequest sellRequest) {
            this.sellRequest = sellRequest;
            return this;
        }

        public Builder buyRequest(final BuyRequest buyRequest) {
            this.buyRequest = buyRequest;
            return this;
        }

        public TradeTransaction build(){
            final var tradeTransaction = new TradeTransaction();
            tradeTransaction.setTransactionId(transactionId);
            tradeTransaction.setProductId(productId);
            tradeTransaction.setAmount(amount);
            tradeTransaction.setQuantity(quantity);
            tradeTransaction.setCreatedTime(createdTime);
            tradeTransaction.setBuyRequest(buyRequest);
            tradeTransaction.setSellRequest(sellRequest);
            return tradeTransaction;
        }
    }
}
