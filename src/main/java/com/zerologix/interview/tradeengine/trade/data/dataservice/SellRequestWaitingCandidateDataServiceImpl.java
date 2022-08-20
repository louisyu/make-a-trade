package com.zerologix.interview.tradeengine.trade.data.dataservice;

import com.zerologix.interview.tradeengine.trade.data.dao.SellRequestWaitingCandidate;
import com.zerologix.interview.tradeengine.trade.data.repository.SellRequestWaitingCandidateRepository;
import com.zerologix.interview.tradeengine.trade.data.transform.SellRequestDtoTransform;
import com.zerologix.interview.tradeengine.trade.data.transform.SellRequestWaitingCandidateDaoTransform;
import com.zerologix.interview.tradeengine.trade.service.dto.BuyRequest;
import com.zerologix.interview.tradeengine.trade.service.dto.SellRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SellRequestWaitingCandidateDataServiceImpl implements SellRequestWaitingCandidateDataService {
    private final SellRequestWaitingCandidateRepository sellRequestWaitingCandidateRepository;

    private final SellRequestDtoTransform sellRequestDtoTransform;
    private final SellRequestWaitingCandidateDaoTransform sellRequestWaitingCandidateDaoTransform;

    @Autowired
    public SellRequestWaitingCandidateDataServiceImpl(final SellRequestWaitingCandidateRepository sellRequestWaitingCandidateRepository, final SellRequestDtoTransform sellRequestDtoTransform, final SellRequestWaitingCandidateDaoTransform sellRequestWaitingCandidateDaoTransform) {
        this.sellRequestWaitingCandidateRepository = sellRequestWaitingCandidateRepository;
        this.sellRequestDtoTransform = sellRequestDtoTransform;
        this.sellRequestWaitingCandidateDaoTransform = sellRequestWaitingCandidateDaoTransform;
    }

    @Override
    public List<SellRequest> allocateSellRequests(final BuyRequest buyRequest) {
        final var pageable = PageRequest.of(0, buyRequest.getRequestQuantity());
        final var waitingList = sellRequestWaitingCandidateRepository.findByProductIdAndAmountOrderByRequestTimeAsc(buyRequest.getProductId(), buyRequest.getRequestAmount(), pageable);
        if (CollectionUtils.isEmpty(waitingList)) {
            return Collections.emptyList();
        }
        int requestQuantity = buyRequest.getRequestQuantity();
        final var allocateSellRequests = new ArrayList<SellRequest>();
        for (var sellRequestWaitingList : waitingList) {
            if (requestQuantity > 0) {
                remove(sellRequestWaitingList);
                allocateSellRequests.add(sellRequestDtoTransform.transform(sellRequestWaitingList));
            }
        }
        return allocateSellRequests;
    }

    @Override
    public void add(final SellRequest sellRequest) {
        final var sellRequestWaitingList = sellRequestWaitingCandidateDaoTransform.transform(sellRequest);
        sellRequestWaitingCandidateRepository.save(sellRequestWaitingList);
    }


    @Override
    public void remove(final SellRequest sellRequest) {
        final var sellRequestWaitingList = sellRequestWaitingCandidateDaoTransform.transform(sellRequest);
        remove(sellRequestWaitingList);
    }

    public void remove(final SellRequestWaitingCandidate sellRequestWaitingCandidate) {
        this.sellRequestWaitingCandidateRepository.delete(sellRequestWaitingCandidate);
    }
}
