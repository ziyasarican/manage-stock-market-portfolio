package com.saricanziya.stockmarketportfolio.service;

import com.saricanziya.stockmarketportfolio.entity.PortfolioEntity;
import com.saricanziya.stockmarketportfolio.entity.TransactionEntity;
import com.saricanziya.stockmarketportfolio.mapper.PortfolioMapper;
import com.saricanziya.stockmarketportfolio.repository.PortfolioRepository;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class PortfolioServiceImpl implements PortfolioService{
    private static final Logger logger = LoggerFactory.getLogger(PortfolioServiceImpl.class);

    PortfolioRepository portfolioRepository;

    public PortfolioServiceImpl(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    @SneakyThrows
    @Override
    public void buyProcess(TransactionEntity transactionEntity) {

        // Silinmek istenene hisse miktarı database'de yok ise hata fırlat
        PortfolioEntity byStockName = portfolioRepository.findByStockName(transactionEntity.getStockName());
        if(byStockName != null){
            byStockName.setTotalCostTRY(byStockName.getTotalCostTRY() + transactionEntity.getCostTRY());
            byStockName.setTotalCostUSD(byStockName.getTotalCostUSD() + transactionEntity.getCostUSD());
            byStockName.setAmount(byStockName.getAmount() + transactionEntity.getAmount());

            portfolioRepository.save(byStockName);
            logger.info("Transaction saved succesfully");
        } else{
            byStockName = PortfolioMapper.transactionToPortfolio(transactionEntity);
            portfolioRepository.save(byStockName);
        }
    }

    @SneakyThrows
    @Override
    public void sellProcess(TransactionEntity transactionEntity) {
        PortfolioEntity byStockName = portfolioRepository.findByStockName(transactionEntity.getStockName());

        // Silinmek istenene hisse miktarı database'de yok ise hata fırlat
        if(byStockName == null || byStockName.getStockName() == null){
            throw new Exception(String.format("%s hissesine sahip değilsiniz.", transactionEntity.getStockName()));
        }
        // Silinmek istenen hisse miktarı sahip olunandan fazla ise hata fırlat
        if(transactionEntity.getAmount() > byStockName.getAmount()){
            throw new Exception("Satış miktarı, sahip olduğunuz hisse miktarından fazla.");
        }

        // Silinmek istenen hisse miktarı sahip olunan miktara eşit ise databese'den sil
        if(transactionEntity.getAmount() == byStockName.getAmount()){
            portfolioRepository.delete(byStockName);
            return;
        }

        // Silinmek istenen hisse var ise ve silinmeye uygun ise maliyet-miktar işlemlerini yap
        byStockName.setTotalCostTRY(byStockName.getTotalCostTRY() - transactionEntity.getCostTRY());
        byStockName.setTotalCostUSD(byStockName.getTotalCostUSD() - transactionEntity.getCostUSD());
        byStockName.setAmount(byStockName.getAmount() - transactionEntity.getAmount());

        portfolioRepository.save(byStockName);
        logger.info("Transaction saved succesfully");

    }

    @Override
    public List<PortfolioEntity> getAll() {
        return portfolioRepository.findAll();
    }

    @Override
    public void updatePrices(Map<String, Float> stockPrices) {

        // Db'deki hisseleri excel dosyasındaki kapanış fiyatlarına göre güncelle ve kar hesapla
        List<PortfolioEntity> portfolioEntityList = portfolioRepository.findAll();

        System.out.println(stockPrices);
        for(PortfolioEntity portfolioEntity: portfolioEntityList){
            String stockName = portfolioEntity.getStockName();
            Float unitStockPrice = stockPrices.get(stockName);
            if(unitStockPrice != null){
                portfolioEntity.setUnitStockPrice(unitStockPrice);
                portfolioEntity.setProfit((portfolioEntity.getAmount()*portfolioEntity.getUnitStockPrice()) - portfolioEntity.getTotalCostTRY());
                portfolioRepository.save(portfolioEntity);
            }
        }

    }
}
