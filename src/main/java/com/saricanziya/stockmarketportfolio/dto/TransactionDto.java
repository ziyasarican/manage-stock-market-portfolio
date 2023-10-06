package com.saricanziya.stockmarketportfolio.dto;

import com.saricanziya.stockmarketportfolio.entity.TransactionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDto {
    private String stockName;
    private int unitStockCostWhole;
    private int unitStockCostFraction;
    private int usdExchangeRateWhole;
    private int usdExchangeRateFraction;
    private int amount;
    private TransactionType transactionType;
    public boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public boolean isDtoValid(TransactionDto transactionDto) {
        if (isNullOrEmpty(transactionDto.getStockName())) {
            return false;
        }
        if (transactionDto.getUnitStockCostWhole() <= 0 || transactionDto.getUnitStockCostFraction() < 0) {
            return false;
        }
        if (transactionDto.getUsdExchangeRateWhole() <= 0 || transactionDto.getUsdExchangeRateFraction() < 0) {
            return false;
        }
        if (transactionDto.getAmount() <= 0) {
            return false;
        }
        return true;
    }



}

