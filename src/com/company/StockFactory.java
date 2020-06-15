package com.company;

import com.ib.client.Contract;

import java.util.ArrayList;
import java.util.List;

public class StockFactory {

    public static List<Stock> getStocks(List<Contract> contracts) {
        List<Stock> stocks = new ArrayList<>();

        for (Contract contract : contracts) {
            stocks.add(new Stock(contract.symbol()));
        }

        return stocks;
    }

}
