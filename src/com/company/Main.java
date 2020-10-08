package com.company;

import com.ib.client.Contract;
import com.ib.client.ContractDetails;
import com.ib.client.ScannerSubscription;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        WatchlistDataRetriever app = new WatchlistDataRetriever(4);
        app.run();

        Contract contract1 = new Contract();
        contract1.symbol("BAC PRC");
        contract1.secType("STK");
        contract1.currency("USD");
        contract1.exchange("SMART");


        Contract contract2 = new Contract();
        contract2.symbol("SPY");
        contract2.secType("STK");
        contract2.currency("USD");
        contract2.exchange("SMART");

        Contract contract3 = new Contract();
        contract3.symbol("EXG");
        contract3.secType("STK");
        contract3.currency("USD");
        contract3.exchange("SMART");

        ContractDetails contractDetails1 = app.getContractDetails(contract1);
        ContractDetails contractDetails2 = app.getContractDetails(contract2);
        ContractDetails contractDetails3 = app.getContractDetails(contract3);
//        List<Stock> stocks = app.getData(List.of(contract1)); // , contract2

        ScannerSubscription ss = new ScannerSubscription();
//        ss.numberOfRows(10);
        ss.instrument("BOND");
        ss.locationCode("BOND.US");
        ss.scanCode("HIGH_BOND_NET_ASK_YIELD_ALL");
        ss.maturityDateAbove("20171001");
        ss.belowPrice(100.1);
        ss.moodyRatingAbove("BA3");
        ss.spRatingAbove("BB-");


        app.stop();
    }
}
