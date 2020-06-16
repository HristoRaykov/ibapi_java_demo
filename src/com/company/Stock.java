package com.company;

import java.math.BigDecimal;

public class Stock {

    private String symbol;
    private BigDecimal priorClose;
    private boolean isPriorCloseUpdated = false;
    private BigDecimal last;
    private boolean isLastUpdated = false;
    private BigDecimal auctionPrice;
    private boolean isAuctionPriceUpdated = false;
    //    private BigDecimal change;
    //    private BigDecimal changePercent;
    private int volume;
    private boolean isVolumeUpdated = false;
    private int averageVolume;
    private boolean isAverageVolumeUpdated = false;
    private int imbalance;
    private boolean isImbalanceUpdated = false;
    private boolean isUpdated = false;

    //    private BigDecimal imbalancePercent;
    //    private BigDecimal closeGap;
    //    private BigDecimal closeGapPercent;
    //    private BigDecimal lastGap;
    //    private BigDecimal lastGapPercent;

    public Stock(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getPriorClose() {
        return priorClose;
    }

    public void setPriorClose(BigDecimal priorClose) {
        this.priorClose = priorClose;
        setPriorCloseUpdated(true);
    }

    public boolean isPriorCloseUpdated() {
        return isPriorCloseUpdated;
    }

    public void setPriorCloseUpdated(boolean priorCloseUpdated) {
        isPriorCloseUpdated = priorCloseUpdated;
    }

    public BigDecimal getLast() {
        return last;
    }

    public void setLast(BigDecimal last) {
        this.last = last;
        setLastUpdated(true);
    }

    public boolean isLastUpdated() {
        return isLastUpdated;
    }

    public void setLastUpdated(boolean lastUpdated) {
        isLastUpdated = lastUpdated;
    }

    public BigDecimal getAuctionPrice() {
        return auctionPrice;
    }

    public void setAuctionPrice(BigDecimal auctionPrice) {
        this.auctionPrice = auctionPrice;
        setAuctionPriceUpdated(true);
    }

    public boolean isAuctionPriceUpdated() {
        return isAuctionPriceUpdated;
    }

    public void setAuctionPriceUpdated(boolean auctionPriceUpdated) {
        isAuctionPriceUpdated = auctionPriceUpdated;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
        setVolumeUpdated(true);
    }

    public boolean isVolumeUpdated() {
        return isVolumeUpdated;
    }

    public void setVolumeUpdated(boolean volumeUpdated) {
        isVolumeUpdated = volumeUpdated;
    }

    public int getAverageVolume() {
        return averageVolume;
    }

    public void setAverageVolume(int averageVolume) {
        this.averageVolume = averageVolume;
        setAverageVolumeUpdated(true);
    }

    public boolean isAverageVolumeUpdated() {
        return isAverageVolumeUpdated;
    }

    public void setAverageVolumeUpdated(boolean averageVolumeUpdated) {
        isAverageVolumeUpdated = averageVolumeUpdated;
    }

    public int getImbalance() {
        return imbalance;
    }

    public void setImbalance(int imbalance) {
        this.imbalance = imbalance;
        setImbalanceUpdated(true);
    }

    public boolean isImbalanceUpdated() {
        return isImbalanceUpdated;
    }

    public void setImbalanceUpdated(boolean imbalanceUpdated) {
        isImbalanceUpdated = imbalanceUpdated;
    }

    public boolean isUpdated() {
        return isPriorCloseUpdated() & isLastUpdated() & isAuctionPriceUpdated() &
                isVolumeUpdated() & isAverageVolumeUpdated() & isImbalanceUpdated();
    }


}
