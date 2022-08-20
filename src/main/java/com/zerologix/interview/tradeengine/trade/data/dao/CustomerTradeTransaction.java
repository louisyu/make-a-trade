package com.zerologix.interview.tradeengine.trade.data.dao;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

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
}
