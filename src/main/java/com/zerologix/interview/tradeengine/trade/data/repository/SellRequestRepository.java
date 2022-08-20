package com.zerologix.interview.tradeengine.trade.data.repository;

import com.zerologix.interview.tradeengine.trade.data.dao.SellRequest;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface SellRequestRepository extends CassandraRepository<SellRequest, String> {
}
