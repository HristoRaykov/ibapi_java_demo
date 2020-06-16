package com.company;

import com.ib.client.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static apidemo.util.Util.sleep;

// 1=bid, 2=ask, 4=last, 6=high, 7=low, 9=close

public class WatchlistDataRetriever implements EWrapper {

    // read messages from TWS
    private final EReaderSignal m_signal;

    // send messages to TWS
    private final EClientSocket m_client;

    // live (1) frozen (2) delayed (3) or delayed frozen (4)
    private final int marketDataType;

    private EReader reader;

    private int nextOrderId;

    private ContractDetails contractDetails;

    private List<Stock> stocks;

    private Map<Integer, Stock> stocksIdMap;

    private Map<Integer, Contract> contractsIdMap;

    private int stocksNumber;

    private int updatedStocks;

    private boolean areStocksUpdated;


    public WatchlistDataRetriever(int marketDataType) {
        this.marketDataType = marketDataType;
        this.m_signal = new EJavaSignal();
        this.m_client = new EClientSocket(this, m_signal);
        this.stocksIdMap = new HashMap<>();
    }


    public void run() {
        //        m_client.reqMarketDataType(marketDataType);
        m_client.eConnect("localhost", 4001, 0);

        reader = new EReader(m_client, m_signal);
        reader.start();
        //An additional thread is created in this program design to empty the messaging queue
        new Thread(() -> {
            while (m_client.isConnected()) {
                m_signal.waitForSignal();
                try {
                    reader.processMsgs();
                } catch (Exception e) {
                    System.out.println("Exception: " + e.getMessage());
                }
            }
        }).start();

        if (nextOrderId < 0) {
            sleep(1000);
        }
    }

    public List<Stock> getData(List<Contract> contracts) {
        areStocksUpdated = false;
        updatedStocks = 0;
        stocksNumber = contracts.size();
        stocks = StockFactory.getStocks(contracts);
        stocksIdMap = IdMapFactory.createIdMap(nextOrderId, stocks);

        contractsIdMap = IdMapFactory.createIdMap(nextOrderId, contracts);

        for (Integer id : contractsIdMap.keySet()) {
            m_client.reqMktData(id, contractsIdMap.get(id), "165, 225", false, false, null);
        }

        while (true){
            if (areStocksUpdated) {
                return stocks;
            }
        }


    }


    public void stop() {
        m_client.eDisconnect();
    }

    private void checkAreStocksUpdated(Stock stock) {
        if (stock.isUpdated()) {
            updatedStocks++;
            if (updatedStocks == stocksNumber) {
                areStocksUpdated = true;
            }
        }
    }


    // fields:
    // 9 - priorClose
    // 4 - last
    // 35 - auctionPrice
    @Override
    public void tickPrice(int tickerId, int field, double price, TickAttrib attrib) {
        Stock stock = stocksIdMap.get(tickerId);
        boolean hasChange = false;
        switch (field) {
            case 4:
                stock.setLast(BigDecimal.valueOf(price));
                hasChange = true;
                break;
            case 9:
                stock.setPriorClose(BigDecimal.valueOf(price));
                hasChange = true;
                break;
            case 35:
                stock.setAuctionPrice(BigDecimal.valueOf(price));
                hasChange = true;
                break;
        }

        if (hasChange) {
            checkAreStocksUpdated(stock);
        }


        System.out.println();
    }



    // 8 - volume
    // 21 - averageVolume
    // 36 - imbalance
    @Override
    public void tickSize(int tickerId, int field, int size) {
        Stock stock = stocksIdMap.get(tickerId);
        boolean hasChange = false;
        switch (field) {
            case 8:
                stock.setVolume(size);
                hasChange = true;
                break;
            case 21:
                stock.setAverageVolume(size);
                hasChange = true;
                break;
            case 36:
                stock.setImbalance(size);
                hasChange = true;
                break;
        }

        if (hasChange) {
            checkAreStocksUpdated(stock);
        }

        System.out.println();
    }

    @Override
    public void tickOptionComputation(int tickerId, int field, double impliedVol, double delta, double optPrice, double pvDividend, double gamma, double vega, double theta, double undPrice) {

    }

    @Override
    public void tickGeneric(int tickerId, int tickType, double value) {

    }

    @Override
    public void tickString(int tickerId, int tickType, String value) {

    }

    @Override
    public void tickEFP(int tickerId, int tickType, double basisPoints, String formattedBasisPoints, double impliedFuture, int holdDays, String futureLastTradeDate, double dividendImpact, double dividendsToLastTradeDate) {

    }

    @Override
    public void orderStatus(int orderId, String status, double filled, double remaining, double avgFillPrice, int permId, int parentId, double lastFillPrice, int clientId, String whyHeld, double mktCapPrice) {

    }

    @Override
    public void openOrder(int orderId, Contract contract, Order order, OrderState orderState) {

    }

    @Override
    public void openOrderEnd() {

    }

    @Override
    public void updateAccountValue(String key, String value, String currency, String accountName) {

    }

    @Override
    public void updatePortfolio(Contract contract, double position, double marketPrice, double marketValue, double averageCost, double unrealizedPNL, double realizedPNL, String accountName) {

    }

    @Override
    public void updateAccountTime(String timeStamp) {

    }

    @Override
    public void accountDownloadEnd(String accountName) {

    }

    @Override
    public void nextValidId(int orderId) {
        nextOrderId = orderId;
    }

    @Override
    public void contractDetails(int reqId, ContractDetails contractDetails) {

    }

    @Override
    public void bondContractDetails(int reqId, ContractDetails contractDetails) {

    }

    @Override
    public void contractDetailsEnd(int reqId) {

    }

    @Override
    public void execDetails(int reqId, Contract contract, Execution execution) {

    }

    @Override
    public void execDetailsEnd(int reqId) {

    }

    @Override
    public void updateMktDepth(int tickerId, int position, int operation, int side, double price, int size) {

    }

    @Override
    public void updateMktDepthL2(int tickerId, int position, String marketMaker, int operation, int side, double price, int size, boolean isSmartDepth) {

    }

    @Override
    public void updateNewsBulletin(int msgId, int msgType, String message, String origExchange) {

    }

    @Override
    public void managedAccounts(String accountsList) {

    }

    @Override
    public void receiveFA(int faDataType, String xml) {

    }

    @Override
    public void historicalData(int reqId, Bar bar) {

    }

    @Override
    public void scannerParameters(String xml) {

    }

    @Override
    public void scannerData(int reqId, int rank, ContractDetails contractDetails, String distance, String benchmark, String projection, String legsStr) {

    }

    @Override
    public void scannerDataEnd(int reqId) {

    }

    @Override
    public void realtimeBar(int reqId, long time, double open, double high, double low, double close, long volume, double wap, int count) {

    }

    @Override
    public void currentTime(long time) {

    }

    @Override
    public void fundamentalData(int reqId, String data) {

    }

    @Override
    public void deltaNeutralValidation(int reqId, DeltaNeutralContract deltaNeutralContract) {

    }

    @Override
    public void tickSnapshotEnd(int reqId) {

    }

    @Override
    public void marketDataType(int reqId, int marketDataType) {

    }

    @Override
    public void commissionReport(CommissionReport commissionReport) {

    }

    @Override
    public void position(String account, Contract contract, double pos, double avgCost) {

    }

    @Override
    public void positionEnd() {

    }

    @Override
    public void accountSummary(int reqId, String account, String tag, String value, String currency) {

    }

    @Override
    public void accountSummaryEnd(int reqId) {

    }

    @Override
    public void verifyMessageAPI(String apiData) {

    }

    @Override
    public void verifyCompleted(boolean isSuccessful, String errorText) {

    }

    @Override
    public void verifyAndAuthMessageAPI(String apiData, String xyzChallenge) {

    }

    @Override
    public void verifyAndAuthCompleted(boolean isSuccessful, String errorText) {

    }

    @Override
    public void displayGroupList(int reqId, String groups) {

    }

    @Override
    public void displayGroupUpdated(int reqId, String contractInfo) {

    }

    @Override
    public void error(Exception e) {

    }

    @Override
    public void error(String str) {

    }

    @Override
    public void error(int id, int errorCode, String errorMsg) {

    }

    @Override
    public void connectionClosed() {

    }

    @Override
    public void connectAck() {

    }

    @Override
    public void positionMulti(int reqId, String account, String modelCode, Contract contract, double pos, double avgCost) {

    }

    @Override
    public void positionMultiEnd(int reqId) {

    }

    @Override
    public void accountUpdateMulti(int reqId, String account, String modelCode, String key, String value, String currency) {

    }

    @Override
    public void accountUpdateMultiEnd(int reqId) {

    }

    @Override
    public void securityDefinitionOptionalParameter(int reqId, String exchange, int underlyingConId, String tradingClass, String multiplier, Set<String> expirations, Set<Double> strikes) {

    }

    @Override
    public void securityDefinitionOptionalParameterEnd(int reqId) {

    }

    @Override
    public void softDollarTiers(int reqId, SoftDollarTier[] tiers) {

    }

    @Override
    public void familyCodes(FamilyCode[] familyCodes) {

    }

    @Override
    public void symbolSamples(int reqId, ContractDescription[] contractDescriptions) {

    }

    @Override
    public void historicalDataEnd(int reqId, String startDateStr, String endDateStr) {

    }

    @Override
    public void mktDepthExchanges(DepthMktDataDescription[] depthMktDataDescriptions) {

    }

    @Override
    public void tickNews(int tickerId, long timeStamp, String providerCode, String articleId, String headline, String extraData) {

    }

    @Override
    public void smartComponents(int reqId, Map<Integer, Map.Entry<String, Character>> theMap) {

    }

    @Override
    public void tickReqParams(int tickerId, double minTick, String bboExchange, int snapshotPermissions) {

    }

    @Override
    public void newsProviders(NewsProvider[] newsProviders) {

    }

    @Override
    public void newsArticle(int requestId, int articleType, String articleText) {

    }

    @Override
    public void historicalNews(int requestId, String time, String providerCode, String articleId, String headline) {

    }

    @Override
    public void historicalNewsEnd(int requestId, boolean hasMore) {

    }

    @Override
    public void headTimestamp(int reqId, String headTimestamp) {

    }

    @Override
    public void histogramData(int reqId, List<HistogramEntry> items) {

    }

    @Override
    public void historicalDataUpdate(int reqId, Bar bar) {

    }

    @Override
    public void rerouteMktDataReq(int reqId, int conId, String exchange) {

    }

    @Override
    public void rerouteMktDepthReq(int reqId, int conId, String exchange) {

    }

    @Override
    public void marketRule(int marketRuleId, PriceIncrement[] priceIncrements) {

    }

    @Override
    public void pnl(int reqId, double dailyPnL, double unrealizedPnL, double realizedPnL) {

    }

    @Override
    public void pnlSingle(int reqId, int pos, double dailyPnL, double unrealizedPnL, double realizedPnL, double value) {

    }

    @Override
    public void historicalTicks(int reqId, List<HistoricalTick> ticks, boolean done) {

    }

    @Override
    public void historicalTicksBidAsk(int reqId, List<HistoricalTickBidAsk> ticks, boolean done) {

    }

    @Override
    public void historicalTicksLast(int reqId, List<HistoricalTickLast> ticks, boolean done) {

    }

    @Override
    public void tickByTickAllLast(int reqId, int tickType, long time, double price, int size, TickAttribLast tickAttribLast, String exchange, String specialConditions) {

    }

    @Override
    public void tickByTickBidAsk(int reqId, long time, double bidPrice, double askPrice, int bidSize, int askSize, TickAttribBidAsk tickAttribBidAsk) {

    }

    @Override
    public void tickByTickMidPoint(int reqId, long time, double midPoint) {

    }

    @Override
    public void orderBound(long orderId, int apiClientId, int apiOrderId) {

    }

    @Override
    public void completedOrder(Contract contract, Order order, OrderState orderState) {

    }

    @Override
    public void completedOrdersEnd() {

    }
}
