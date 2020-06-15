package com.company;

import com.ib.client.Contract;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        WatchlistDataRetriever app = new WatchlistDataRetriever();
        app.run();

        Contract contract = new Contract();
        contract.symbol("AAPL");
        contract.secType("STK");
        contract.currency("USD");
        contract.exchange("SMART");

        var stock = app.getData(List.of(contract));

        app.stop();
    }
}
