package com.zerologix.interview.tradeengine.trade.web.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * BuySellRequest is a request for BuyRequest(/buy/request) and SellRequest(/sell/request) API
 */
public class BuySellRequest {

    private String productId;

    private String customerId;

    private BigDecimal requestAmount;

    private int requestQuantity;

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
        final BuySellRequest that = (BuySellRequest) o;
        return requestQuantity == that.requestQuantity && Objects.equals(productId, that.productId) && Objects.equals(customerId, that.customerId) && Objects.equals(requestAmount, that.requestAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, customerId, requestAmount, requestQuantity);
    }
}
