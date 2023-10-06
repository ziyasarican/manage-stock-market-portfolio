package com.saricanziya.stockmarketportfolio.controller;

import com.saricanziya.stockmarketportfolio.dto.TransactionDto;
import com.saricanziya.stockmarketportfolio.entity.TransactionEntity;
import com.saricanziya.stockmarketportfolio.entity.TransactionType;
import com.saricanziya.stockmarketportfolio.mapper.TransactionMapper;
import com.saricanziya.stockmarketportfolio.service.PortfolioService;
import com.saricanziya.stockmarketportfolio.service.TransactionService;
import com.saricanziya.stockmarketportfolio.util.StockDataLoader;
import lombok.SneakyThrows;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping
public class PortfolioController {
    private TransactionService transactionService;
    private StockDataLoader stockDataLoader;

    private PortfolioService portfolioService;

    public PortfolioController(TransactionService transactionService, StockDataLoader stockDataLoader, PortfolioService portfolioService) {
        this.transactionService = transactionService;
        this.stockDataLoader = stockDataLoader;
        this.portfolioService = portfolioService;
    }

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @SneakyThrows
    @GetMapping("/add-transaction")
    public String showTransactionForm(Model model) {
        model.addAttribute("transactionDto", new TransactionDto());
        List<String> stockNames = stockDataLoader.loadStockNames();
        model.addAttribute("stockNames", stockNames);

        return "addTransaction";
    }

    @SneakyThrows
    @PostMapping("save")
    public String saveTransaction(@ModelAttribute("transactionDto") TransactionDto transactionDto, Model model){
        // Kullanıcının veri girişini kontrol et
        model.addAttribute("transactionDto", new TransactionDto());
        List<String> stockNames = stockDataLoader.loadStockNames();
        model.addAttribute("stockNames", stockNames);
        if (!transactionDto.isDtoValid(transactionDto)) {
            model.addAttribute("errorMessage", "Geçersiz veri girişi. Lütfen tüm alanları doğru şekilde doldurun.");
            return "addTransaction";
        }

        TransactionEntity transactionEntity = TransactionMapper.dtoToEntity(transactionDto);
        // Buy işlemi gerçekleştirilmek isteniyorsa
        if (transactionEntity.getTransactionType().equals(TransactionType.BUY)){
            portfolioService.buyProcess(transactionEntity);
            model.addAttribute("successMessage", "Alış işlemi başarıyla gerçekleşti.");
            transactionService.save(transactionEntity);
            return "addTransaction";
        }

        // Buy'a girmezse Sell'e girecektir. İki seçenekten biri seçiliyor çünkü.
        try {
            portfolioService.sellProcess(transactionEntity);
            model.addAttribute("successMessage", "Satış işlemi başarıyla gerçekleşti.");
            transactionService.save(transactionEntity);
            return "addTransaction";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Satış işlemi başarısız: " + e.getMessage());
            return "addTransaction";
        }
    }

    @GetMapping("/portfolio")
    public String showPortfolio(Model model) {
        model.addAttribute("portfolioList", portfolioService.getAll());
        return "portfolio";
    }

    // Fiyatları Güncelle butonu ile excel dosyasından güncel hisse fiyatını çekip kar hesaplama
    @PostMapping("/update-stock-prices")
    public String updateStockPrices(Model model) throws IOException {
        Map<String, Float> stockPrices = stockDataLoader.loadStockPrices();
        portfolioService.updatePrices(stockPrices);
        model.addAttribute("updatedStockPrices", stockPrices);
        return "redirect:/portfolio";
    }
}
