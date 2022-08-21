package com.zerologix.interview.tradeengine.trade.data.dataservice;

import com.zerologix.interview.tradeengine.trade.data.dao.CustomerTradeTransaction;
import com.zerologix.interview.tradeengine.trade.data.dao.TradeType;
import com.zerologix.interview.tradeengine.trade.data.repository.CustomerTradeTransactionRepository;
import com.zerologix.interview.tradeengine.trade.service.dto.TradeTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerTradeTransactionDataServiceImpl implements CustomerTradeTransactionDataService {
    private final CustomerTradeTransactionRepository customerTradeTransactionRepository;

    @Autowired
    public CustomerTradeTransactionDataServiceImpl(final CustomerTradeTransactionRepository customerTradeTransactionRepository) {
        this.customerTradeTransactionRepository = customerTradeTransactionRepository;
    }

    @Override
    public void createCustomerTradeTransaction(final TradeTransaction tradeTransaction) {
        createBuyCustomerTradeTransaction(tradeTransaction);
        createSellCustomerTradeTransaction(tradeTransaction);
    }

    private void createBuyCustomerTradeTransaction(final TradeTransaction tradeTransaction) {
        final var customerTradeTransaction = new CustomerTradeTransaction(tradeTransaction.getBuyRequest().getCustomerId(), TradeType.BUY.name(), tradeTransaction.getTransactionId());
        customerTradeTransactionRepository.save(customerTradeTransaction);
    }

    private void createSellCustomerTradeTransaction(final TradeTransaction tradeTransaction) {
        final var customerTradeTransaction = new CustomerTradeTransaction(tradeTransaction.getSellRequest().getCustomerId(), TradeType.SELL.name(), tradeTransaction.getTransactionId());
        customerTradeTransactionRepository.save(customerTradeTransaction);
    }
}
