package com.zerologix.interview.tradeengine.trade.data.dao;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * BuyRequest represents a cassandra table BuyRequest.
 */
@Table
public class BuyRequest {

    @PrimaryKeyColumn(name = "request_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String requestId;

    @Column("product_id")
    private String productId;

    @Column("customer_id")
    private String customerId;

    @Column("request_amount")
    private BigDecimal requestAmount;

    @Column("request_quantity")
    private int requestQuantity;

    @Column("request_time")
    private Date requestTime;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(final String requestId) {
        this.requestId = requestId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(final String productId) {
        this.productId = productId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(final String customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(final BigDecimal requestAmount) {
        this.requestAmount = requestAmount;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(final Date requestTime) {
        this.requestTime = requestTime;
    }

    public int getRequestQuantity() {
        return requestQuantity;
    }

    public void setRequestQuantity(final int requestQuantity) {
        this.requestQuantity = requestQuantity;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BuyRequest that = (BuyRequest) o;
        return requestQuantity == that.requestQuantity && Objects.equals(requestId, that.requestId) && Objects.equals(productId, that.productId) && Objects.equals(customerId, that.customerId) && Objects.equals(requestAmount, that.requestAmount) && Objects.equals(requestTime, that.requestTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId, productId, customerId, requestAmount, requestQuantity, requestTime);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String requestId;
        private String productId;
        private String customerId;
        private BigDecimal requestAmount;
        private Date requestTime;
        private int requestQuantity;

        public Builder requestId(final String requestId) {
            this.requestId = requestId;
            return this;
        }

        public Builder productId(final String productId) {
            this.productId = productId;
            return this;
        }

        public Builder customerId(final String customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder requestAmount(final BigDecimal requestAmount) {
            this.requestAmount = requestAmount;
            return this;
        }

        public Builder requestTime(final Date requestTime) {
            this.requestTime = requestTime;
            return this;
        }

        public Builder requestQuantity(final int requestQuantity) {
            this.requestQuantity = requestQuantity;
            return this;
        }

        public BuyRequest build() {
            final var buyRequest = new BuyRequest();
            buyRequest.setRequestId(requestId);
            buyRequest.setProductId(productId);
            buyRequest.setCustomerId(customerId);
            buyRequest.setRequestAmount(requestAmount);
            buyRequest.setRequestTime(requestTime);
            buyRequest.setRequestQuantity(requestQuantity);
            return buyRequest;
        }
    }
}
