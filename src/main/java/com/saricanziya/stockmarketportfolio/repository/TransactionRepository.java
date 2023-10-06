package com.saricanziya.stockmarketportfolio.repository;

import com.saricanziya.stockmarketportfolio.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
}
