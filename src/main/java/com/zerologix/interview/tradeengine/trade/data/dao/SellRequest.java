package com.zerologix.interview.tradeengine.trade.data.dao;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.util.Date;

@Table
public class SellRequest {

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

        public SellRequest build() {
            final var sellRequest = new SellRequest();
            sellRequest.setRequestId(requestId);
            sellRequest.setProductId(productId);
            sellRequest.setCustomerId(customerId);
            sellRequest.setRequestAmount(requestAmount);
            sellRequest.setRequestTime(requestTime);
            sellRequest.setRequestQuantity(requestQuantity);
            return sellRequest;
        }
    }
}
