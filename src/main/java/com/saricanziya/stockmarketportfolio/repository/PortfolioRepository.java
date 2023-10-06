package com.saricanziya.stockmarketportfolio.repository;

import com.saricanziya.stockmarketportfolio.entity.PortfolioEntity;
import com.saricanziya.stockmarketportfolio.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Long> {
    PortfolioEntity findByStockName(String stockName);

}
