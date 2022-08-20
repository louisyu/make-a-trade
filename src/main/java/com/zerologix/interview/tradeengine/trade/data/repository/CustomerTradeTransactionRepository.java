package com.zerologix.interview.tradeengine.trade.data.repository;

import com.zerologix.interview.tradeengine.trade.data.dao.CustomerTradeTransaction;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;

public interface CustomerTradeTransactionRepository extends CassandraRepository<CustomerTradeTransaction, String> {
    List<CustomerTradeTransaction> findByCustomerId(String customerId);
}
