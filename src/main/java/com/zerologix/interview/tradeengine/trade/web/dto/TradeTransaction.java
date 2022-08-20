package com.zerologix.interview.tradeengine.trade.web.dto;

import com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest;
import com.zerologix.interview.tradeengine.trade.service.dto.SellRequest;

import java.math.BigDecimal;
import java.util.Date;

public class TradeTransaction {
    private String transactionId;
    private String productId;
    private BigDecimal amount;
    private int quantity;
    private Date createdTime;

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

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private String transactionId;
        private String productId;
        private BigDecimal amount;
        private int quantity;
        private Date createdTime;

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

        public TradeTransaction build(){
            final var tradeTransaction = new TradeTransaction();
            tradeTransaction.setTransactionId(transactionId);
            tradeTransaction.setProductId(productId);
            tradeTransaction.setAmount(amount);
            tradeTransaction.setQuantity(quantity);
            tradeTransaction.setCreatedTime(createdTime);
            return tradeTransaction;
        }
    }
}
