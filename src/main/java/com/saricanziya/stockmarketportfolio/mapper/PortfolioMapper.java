package com.saricanziya.stockmarketportfolio.mapper;

import com.saricanziya.stockmarketportfolio.entity.PortfolioEntity;
import com.saricanziya.stockmarketportfolio.entity.TransactionEntity;
import org.mapstruct.Mapper;

@Mapper
public class PortfolioMapper {
    public static PortfolioEntity transactionToPortfolio(TransactionEntity transactionEntity){
        PortfolioEntity portfolioEntity = new PortfolioEntity();
        portfolioEntity.setStockName(transactionEntity.getStockName());
        portfolioEntity.setAmount(transactionEntity.getAmount());
        portfolioEntity.setTotalCostTRY(transactionEntity.getCostTRY());
        portfolioEntity.setTotalCostUSD(transactionEntity.getCostUSD());
        portfolioEntity.setUnitStockPrice(transactionEntity.getUnitStockCost());
        return portfolioEntity;
    }
}
