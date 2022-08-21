package com.zerologix.interview.tradeengine.trade.data.dao;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Objects;

@Table
public class CustomerTradeTransaction {

    @PrimaryKeyColumn(name = "customer_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String customerId;

    @PrimaryKeyColumn(name = "type", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private String type;

    @PrimaryKeyColumn(name = "transaction_id", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    private String transactionId;

    public CustomerTradeTransaction() {
    }

    public CustomerTradeTransaction(final String customerId, final String type, final String transactionId) {
        this.customerId = customerId;
        this.type = type;
        this.transactionId = transactionId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(final String customerId) {
        this.customerId = customerId;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(final String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CustomerTradeTransaction that = (CustomerTradeTransaction) o;
        return Objects.equals(customerId, that.customerId) && Objects.equals(type, that.type) && Objects.equals(transactionId, that.transactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, type, transactionId);
    }
}
