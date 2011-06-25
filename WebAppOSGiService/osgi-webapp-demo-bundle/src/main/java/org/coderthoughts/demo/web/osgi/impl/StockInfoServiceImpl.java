package org.coderthoughts.demo.web.osgi.impl;

import java.util.Random;

import org.coderthoughts.demo.web.osgi.api.StockInfoService;
import org.coderthoughts.demo.web.osgi.api.StockQuote;

public class StockInfoServiceImpl implements StockInfoService {
    public static final long DELAY = 15;

    private Random random = new Random();

    public StockQuote getStockQuote(String symbol) {
        return new StockQuote() {
            public long getPrice() {
                return random.nextInt(10000) - 5000;
            }

            public String getCurrency() {
                return "USD";
            }

            public long getDelay() {
                return DELAY;
            }
        };
    }
}
