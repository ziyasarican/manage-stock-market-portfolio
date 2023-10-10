package com.saricanziya.stockmarketportfolio.mapper;

import com.saricanziya.stockmarketportfolio.dto.TransactionDto;
import com.saricanziya.stockmarketportfolio.entity.TransactionEntity;
import org.mapstruct.Mapper;

@Mapper
public class TransactionMapper {
    public static TransactionEntity dtoToEntity(TransactionDto dto) {
        TransactionEntity entity = new TransactionEntity();
        entity.setStockName(dto.getStockName());
        float unitStockCost = Float.parseFloat(dto.getUnitStockCostWhole() + "." + dto.getUnitStockCostFraction());
        entity.setUnitStockCost(unitStockCost);
        float usdExchangeRate = Float.parseFloat(dto.getUsdExchangeRateWhole() + "." + dto.getUsdExchangeRateFraction());
        entity.setUsdExchangeRate(usdExchangeRate);
        entity.setAmount(dto.getAmount());
        entity.setTransactionType(dto.getTransactionType());
        entity.setCostTRY(entity.getUnitStockCost() * dto.getAmount());
        entity.setCostUSD(entity.getCostTRY() / entity.getUsdExchangeRate());
        return entity;
    }

}
