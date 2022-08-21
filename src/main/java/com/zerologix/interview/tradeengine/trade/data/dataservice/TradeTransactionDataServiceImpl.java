package com.zerologix.interview.tradeengine.trade.data.dataservice;

import com.zerologix.interview.tradeengine.trade.data.dao.CustomerTradeTransaction;
import com.zerologix.interview.tradeengine.trade.data.repository.CustomerTradeTransactionRepository;
import com.zerologix.interview.tradeengine.trade.data.repository.TradeTransactionRepository;
import com.zerologix.interview.tradeengine.trade.data.transform.TradeTransactionDaoTransform;
import com.zerologix.interview.tradeengine.trade.service.dto.TradeTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TradeTransactionDataServiceImpl implements TradeTransactionDataService {
    private final TradeTransactionRepository tradeTransactionRepository;
    private final CustomerTradeTransactionRepository customerTradeTransactionRepository;
    private final TradeTransactionDaoTransform tradeTransactionDaoTransform;

    @Autowired
    public TradeTransactionDataServiceImpl(final TradeTransactionRepository tradeTransactionRepository, final CustomerTradeTransactionRepository customerTradeTransactionRepository, final TradeTransactionDaoTransform tradeTransactionDaoTransform) {
        this.tradeTransactionRepository = tradeTransactionRepository;
        this.customerTradeTransactionRepository = customerTradeTransactionRepository;
        this.tradeTransactionDaoTransform = tradeTransactionDaoTransform;
    }

    @Override
    public TradeTransaction createTradeTransaction(final TradeTransaction tradeTransaction) {
        final var tradeTransactionDao = tradeTransactionDaoTransform.transform(tradeTransaction);
        return tradeTransactionDaoTransform.transform(tradeTransactionRepository.save(tradeTransactionDao));

    }

    @Override
    public Optional<TradeTransaction> getTradeTransaction(final String tradeTransactionId) {
        final var tradeTransaction = tradeTransactionRepository.findById(tradeTransactionId);
        return tradeTransaction.map(tradeTransactionDaoTransform::transform);
    }

    @Override
    public List<TradeTransaction> findTradeTransactionByCustomerId(final String customerId) {
        return customerTradeTransactionRepository.findByCustomerId(customerId).stream()
                .filter(Objects::nonNull)
                .map(CustomerTradeTransaction::getTransactionId)
                .map(this::getTradeTransaction)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}
