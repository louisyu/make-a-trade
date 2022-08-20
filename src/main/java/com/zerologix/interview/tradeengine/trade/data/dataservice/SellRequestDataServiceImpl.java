package com.zerologix.interview.tradeengine.trade.data.dataservice;


import com.zerologix.interview.tradeengine.trade.data.repository.SellRequestRepository;
import com.zerologix.interview.tradeengine.trade.data.transform.SellRequestDaoTransform;
import com.zerologix.interview.tradeengine.trade.service.dto.SellRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellRequestDataServiceImpl implements SellRequestDataService {
    private final SellRequestRepository sellRequestRepository;
    private final SellRequestDaoTransform sellRequestDaoTransform;

    @Autowired
    public SellRequestDataServiceImpl(final SellRequestRepository sellRequestRepository, final SellRequestDaoTransform sellRequestDaoTransform) {
        this.sellRequestRepository = sellRequestRepository;
        this.sellRequestDaoTransform = sellRequestDaoTransform;
    }

    @Override
    public Optional<SellRequest> findSellRequest(final String id) {
        final var sellRequestDao = sellRequestRepository.findById(id);
        return sellRequestDao.map(sellRequestDaoTransform::transform);
    }

    @Override
    public SellRequest createSellRequest(final SellRequest sellRequest) {
        final var sellRequestDao = sellRequestDaoTransform.transform(sellRequest);
        final var createdSellRequestDao = sellRequestRepository.save(sellRequestDao);
        return sellRequestDaoTransform.transform(createdSellRequestDao);
    }

    @Override
    public void deleteSellRequest(final SellRequest buyRequest) {
        final var sellRequestDao = sellRequestRepository.findById(buyRequest.getRequestId());
        sellRequestDao.ifPresent(sellRequestRepository::delete);
    }
}
