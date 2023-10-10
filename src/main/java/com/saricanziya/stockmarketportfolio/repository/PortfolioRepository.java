package com.saricanziya.stockmarketportfolio.repository;

import com.saricanziya.stockmarketportfolio.entity.PortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Long> {
    PortfolioEntity findByStockName(String stockName);

}
