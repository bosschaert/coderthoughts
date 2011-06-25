package org.coderthoughts.demo.web.osgi.api;

public interface StockInfoService {
    StockQuote getStockQuote(String symbol);
}
