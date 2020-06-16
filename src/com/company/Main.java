package com.company;

import com.ib.client.Contract;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        WatchlistDataRetriever app = new WatchlistDataRetriever(2);
        app.run();

        Contract contract1 = new Contract();
        contract1.symbol("AAPL");
        contract1.secType("STK");
        contract1.currency("USD");
        contract1.exchange("SMART");

        Contract contract2 = new Contract();
        contract2.symbol("IBM");
        contract2.secType("STK");
        contract2.currency("USD");
        contract2.exchange("SMART");

        List<Stock> stocks = app.getData(List.of(contract1, contract2));

        app.stop();
    }
}
