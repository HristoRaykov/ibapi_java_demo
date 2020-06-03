package com.company;

import com.ib.client.Contract;
import com.ib.client.EWrapper;

public class Main {

    public static void main(String[] args) {
        EWrapperImpl app = new EWrapperImpl();
        app.run();

        Contract contract = new Contract();
        contract.symbol("AAPL");
        contract.secType("STK");
        contract.currency("USD");
        contract.exchange("SMART");

        app.getData(contract);

        app.stop();
    }
}
