package com.zerologix.interview.tradeengine.trade.data.dataservice;

import com.zerologix.interview.tradeengine.trade.data.dao.CustomerTradeTransaction;
import com.zerologix.interview.tradeengine.trade.data.dao.TradeType;
import com.zerologix.interview.tradeengine.trade.data.repository.CustomerTradeTransactionRepository;
import com.zerologix.interview.tradeengine.trade.data.repository.TradeTransactionRepository;
import com.zerologix.interview.tradeengine.trade.data.transform.TradeTransactionDaoTransform;
import com.zerologix.interview.tradeengine.trade.service.dto.TradeTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
        final var createdTradeTransaction = tradeTransactionDaoTransform.transform(tradeTransactionRepository.save(tradeTransactionDao));

        final var buyCustomerTradeTransaction = new CustomerTradeTransaction(tradeTransaction.getBuyRequest().getCustomerId(), TradeType.BUY.name(), tradeTransaction.getTransactionId());
        customerTradeTransactionRepository.save(buyCustomerTradeTransaction);

        final var sellCustomerTradeTransaction = new CustomerTradeTransaction(tradeTransaction.getSellRequest().getCustomerId(), TradeType.SELL.name(), tradeTransaction.getTransactionId());
        customerTradeTransactionRepository.save(sellCustomerTradeTransaction);

        return createdTradeTransaction;
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
                .collect(Collectors.toList());
    }
}
