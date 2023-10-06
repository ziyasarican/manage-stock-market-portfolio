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
@Table(name = "transaction")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="stock_name")
    private String stockName;

    @Column(name="unit_stock_cost")
    private float unitStockCost;

    @Column(name="usd_exchange_rate")
    private float usdExchangeRate;

    @Column(name="amount")
    private int amount;

    @Enumerated(EnumType.STRING)
    @Column(name="transaction_type")
    private TransactionType transactionType;

    @Column(name="cost_TRY")
    private float costTRY;

    @Column(name="cost_USD")
    private float costUSD;

}
