package com.zerologix.interview.tradeengine.trade.service.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * SellRequest contains the sell request data. The object goes among the services in the system.
 */
public class SellRequest {

    private String requestId;

    private String productId;

    private String customerId;

    private BigDecimal requestAmount;

    private Date requestTime;

    private int requestQuantity;

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
        final SellRequest that = (SellRequest) o;
        return requestQuantity == that.requestQuantity && Objects.equals(requestId, that.requestId) && Objects.equals(productId, that.productId) && Objects.equals(customerId, that.customerId) && Objects.equals(requestAmount, that.requestAmount) && Objects.equals(requestTime, that.requestTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId, productId, customerId, requestAmount, requestTime, requestQuantity);
    }

    public static Builder builder(){
        return new Builder();
    }
    public static class Builder{
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

        public SellRequest build(){
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
