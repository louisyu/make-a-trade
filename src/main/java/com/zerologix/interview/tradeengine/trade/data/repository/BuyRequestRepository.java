package com.zerologix.interview.tradeengine.trade.data.repository;

import com.zerologix.interview.tradeengine.trade.data.dao.BuyRequest;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface BuyRequestRepository extends CassandraRepository<BuyRequest, String> {
}
