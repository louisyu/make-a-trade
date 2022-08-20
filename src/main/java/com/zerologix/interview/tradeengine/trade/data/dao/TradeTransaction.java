package com.zerologix.interview.tradeengine.trade.data.dao;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.util.Date;

@Table
public class TradeTransaction {

    @PrimaryKeyColumn(name = "transaction_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String transactionId;

    @Column("product_id")
    private String productId;

    @Column("amount")
    private BigDecimal amount;

    @Column("quantity")
    private int quantity;

    @Column("created_time")
    private Date createdTime;

    @Column("sell_request_id")
    private String sellRequestId;

    @Column("buy_request_id")
    private String buyRequestId;

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

    public String getSellRequestId() {
        return sellRequestId;
    }

    public void setSellRequestId(final String sellRequestId) {
        this.sellRequestId = sellRequestId;
    }

    public String getBuyRequestId() {
        return buyRequestId;
    }

    public void setBuyRequestId(final String buyRequestId) {
        this.buyRequestId = buyRequestId;
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
        private String sellRequestId;
        private String buyRequestId;

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

        public Builder sellRequestId(final String sellRequestId) {
            this.sellRequestId = sellRequestId;
            return this;
        }

        public Builder buyRequestId(final String buyRequestId) {
            this.buyRequestId = buyRequestId;
            return this;
        }

        public TradeTransaction build() {
            final var tradeTransaction = new TradeTransaction();
            tradeTransaction.setTransactionId(transactionId);
            tradeTransaction.setProductId(productId);
            tradeTransaction.setAmount(amount);
            tradeTransaction.setQuantity(quantity);
            tradeTransaction.setCreatedTime(createdTime);
            tradeTransaction.setSellRequestId(sellRequestId);
            tradeTransaction.setBuyRequestId(buyRequestId);
            return tradeTransaction;
        }
    }
}
