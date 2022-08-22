package com.zerologix.interview.tradeengine.trade.web.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * A response of Trade Transaction API(/transaction).
 */
public class TradeTransactionResponse {
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TradeTransactionResponse that = (TradeTransactionResponse) o;
        return quantity == that.quantity && Objects.equals(transactionId, that.transactionId) && Objects.equals(productId, that.productId) && Objects.equals(amount, that.amount) && Objects.equals(createdTime, that.createdTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, productId, amount, quantity, createdTime);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
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

        public TradeTransactionResponse build() {
            final var tradeTransaction = new TradeTransactionResponse();
            tradeTransaction.setTransactionId(transactionId);
            tradeTransaction.setProductId(productId);
            tradeTransaction.setAmount(amount);
            tradeTransaction.setQuantity(quantity);
            tradeTransaction.setCreatedTime(createdTime);
            return tradeTransaction;
        }
    }
}
