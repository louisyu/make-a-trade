package com.zerologix.interview.tradeengine.trade.data.dataservice;


import com.zerologix.interview.tradeengine.trade.data.repository.BuyRequestRepository;
import com.zerologix.interview.tradeengine.trade.data.transform.BuyRequestDaoTransform;
import com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * {@inheritDoc}
 */
@Service
public class BuyRequestDataServiceImpl implements BuyRequestDataService {
    private final BuyRequestRepository buyRequestRepository;
    private final BuyRequestDaoTransform buyRequestDaoTransform;

    @Autowired
    public BuyRequestDataServiceImpl(final BuyRequestRepository buyRequestRepository, final BuyRequestDaoTransform buyRequestDaoTransform) {
        this.buyRequestRepository = buyRequestRepository;
        this.buyRequestDaoTransform = buyRequestDaoTransform;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<BuyRequest> findBuyRequest(final String requestId) {
        final var buyRequestDao = buyRequestRepository.findById(requestId);
        return buyRequestDao.map(buyRequestDaoTransform::transform);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuyRequest createBuyRequest(final BuyRequest buyRequest) {
        final var buyRequestDao = buyRequestDaoTransform.transform(buyRequest);
        final var createdBuyRequestDao = buyRequestRepository.save(buyRequestDao);
        return buyRequestDaoTransform.transform(createdBuyRequestDao);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteBuyRequest(final BuyRequest buyRequest) {
        final var buyRequestDao = buyRequestRepository.findById(buyRequest.getRequestId());
        buyRequestDao.ifPresent(buyRequestRepository::delete);
    }
}
