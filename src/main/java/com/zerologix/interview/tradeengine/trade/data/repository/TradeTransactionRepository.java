package com.zerologix.interview.tradeengine.trade.data.repository;

import com.zerologix.interview.tradeengine.trade.data.dao.TradeTransaction;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface TradeTransactionRepository extends CassandraRepository<TradeTransaction, String> {
}
