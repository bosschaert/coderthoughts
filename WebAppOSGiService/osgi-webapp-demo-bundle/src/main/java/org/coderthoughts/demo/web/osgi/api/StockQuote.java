package org.coderthoughts.demo.web.osgi.api;

public interface StockQuote {
    long getPrice();
    String getCurrency();
    long getDelay();
}
