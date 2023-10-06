package com.saricanziya.stockmarketportfolio.util;

import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Component
public class StockDataLoader {


    // Excelden hisse isimlerini çekme
    public List<String> loadStockNames() throws IOException {
        List<String> stockNames = new ArrayList<>();
        File file = ResourceUtils.getFile("classpath:tumhisse.xlsx");

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Cell cell = row.getCell(0);

                if (cell != null && cell.getCellType() == CellType.STRING) {
                    String cellValue = cell.getStringCellValue();
                    cellValue = cellValue.replaceAll("[^\\p{L}\\p{N}]", "");
                    stockNames.add(cellValue.trim());
                }
            }
        }

        return stockNames;
    }

    // Excelden hisse isim-fiyat bilgilerini çekme
    public Map<String, Float> loadStockPrices() throws  IOException{
        Map<String, Float> stockData = new HashMap<>();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("tumhisse.xlsx");

            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // İlk sayfayı alın, gerektiğinde değiştirin

            Iterator<Row> rowIterator = sheet.iterator();

            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Cell stockCell = row.getCell(0);
                Cell priceCell = row.getCell(1);

                if (stockCell != null && priceCell != null) {
                    String stockName = stockCell.getStringCellValue();
                    stockName = stockName.replaceAll("[^\\p{L}\\p{N}]", "");
                    float stockPrice = (float) priceCell.getNumericCellValue();

                    stockData.put(stockName, stockPrice);
                }
            }

            workbook.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stockData;

    }
}
