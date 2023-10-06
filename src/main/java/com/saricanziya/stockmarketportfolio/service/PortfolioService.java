package com.saricanziya.stockmarketportfolio.service;

import com.saricanziya.stockmarketportfolio.entity.PortfolioEntity;
import com.saricanziya.stockmarketportfolio.entity.TransactionEntity;

import java.util.List;
import java.util.Map;

public interface PortfolioService {
    void buyProcess(TransactionEntity transactionEntity);

    void sellProcess(TransactionEntity transactionEntity);

    List<PortfolioEntity> getAll();

    void updatePrices(Map<String, Float> stockPrices);
}
