package com.zerologix.interview.tradeengine.trade.data.repository;

import com.zerologix.interview.tradeengine.trade.data.dao.SellRequestWaitingCandidate;
import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface SellRequestWaitingCandidateRepository extends CassandraRepository<SellRequestWaitingCandidate, MapId> {

    @Query("SELECT s FROM SellRequestWaitingCandidate s WHERE s.productId=?0 and s.amount=?1 order by s.requestTime ASC LIMIT ?2")
    List<SellRequestWaitingCandidate> findByProductIdAndAmountOrderByRequestTimeAsc(String productId, BigDecimal amount, Pageable pageable);
}
