package com.zerologix.interview.tradeengine.trade.data.dao;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Table
public class SellRequestWaitingCandidate {

    @PrimaryKeyColumn(name = "product_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String productId;

    @PrimaryKeyColumn(name = "amount", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private BigDecimal amount;

    @PrimaryKeyColumn(name = "request_time", ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private Date requestTime;

    @PrimaryKeyColumn(name = "sell_request_id", ordinal = 3, type = PrimaryKeyType.CLUSTERED)
    private String sellRequestId;

    private int quantity;

    private String customerId;

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

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(final Date requestTime) {
        this.requestTime = requestTime;
    }

    public String getSellRequestId() {
        return sellRequestId;
    }

    public void setSellRequestId(final String sellRequestId) {
        this.sellRequestId = sellRequestId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(final String customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final SellRequestWaitingCandidate that = (SellRequestWaitingCandidate) o;
        return quantity == that.quantity && Objects.equals(productId, that.productId) && Objects.equals(amount, that.amount) && Objects.equals(requestTime, that.requestTime) && Objects.equals(sellRequestId, that.sellRequestId) && Objects.equals(customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, amount, requestTime, sellRequestId, quantity, customerId);
    }

    public static Builder builder(){
        return new Builder();
    }
    public static class Builder{
        private String productId;
        private BigDecimal amount;
        private int quantity;
        private Date requestTime;
        private String sellRequestId;
        private String customerId;

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

        public Builder requestTime(final Date requestTime) {
            this.requestTime = requestTime;
            return this;
        }

        public Builder sellRequestId(final String sellRequestId) {
            this.sellRequestId = sellRequestId;
            return this;
        }

        public Builder customerId(final String customerId) {
            this.customerId = customerId;
            return this;
        }

        public SellRequestWaitingCandidate build(){
            final var sellRequestWaitingCandidate = new SellRequestWaitingCandidate();
            sellRequestWaitingCandidate.setSellRequestId(sellRequestId);
            sellRequestWaitingCandidate.setProductId(productId);
            sellRequestWaitingCandidate.setAmount(amount);
            sellRequestWaitingCandidate.setQuantity(quantity);
            sellRequestWaitingCandidate.setRequestTime(requestTime);
            sellRequestWaitingCandidate.setCustomerId(customerId);
            return sellRequestWaitingCandidate;
        }
    }
}
