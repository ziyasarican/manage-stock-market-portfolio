package com.saricanziya.stockmarketportfolio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "portfolio")
public class PortfolioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="stock_name", unique=true)
    private String stockName;

    @Column(name="amount")
    private int amount;

    @Column(name="unit_stock_price")
    private float unitStockPrice;

    @Column(name="total_cost_TRY")
    private float totalCostTRY;

    @Column(name="total_cost_USD")
    private float totalCostUSD;

    @Column(name="profit")
    private float profit;
}
