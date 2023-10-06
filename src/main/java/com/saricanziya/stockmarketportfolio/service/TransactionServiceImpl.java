package com.saricanziya.stockmarketportfolio.service;

import com.saricanziya.stockmarketportfolio.entity.PortfolioEntity;
import com.saricanziya.stockmarketportfolio.entity.TransactionEntity;
import com.saricanziya.stockmarketportfolio.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{
    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void save(TransactionEntity transactionEntity) {
        transactionRepository.save(transactionEntity);
        logger.info("Transaction saved succesfully");
    }
}
