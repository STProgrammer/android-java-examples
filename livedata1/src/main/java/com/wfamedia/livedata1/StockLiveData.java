package com.wfamedia.livedata1;

import androidx.lifecycle.LiveData;

import java.math.BigDecimal;

// DENNE ER POENGET!!
// Subklasse av LiveData<..>
public class StockLiveData extends LiveData<BigDecimal> {
    private StockManager stockManager;

    private SimplePriceListener listener = new SimplePriceListener() {
        @Override
        public void onPriceChanged(BigDecimal price) {
            postValue(price);
        }
    };

    public StockLiveData(String symbol) {
        stockManager = new StockManager(symbol);
    }

    @Override
    protected void onActive() {
        stockManager.requestPriceUpdates(listener);
    }

    @Override
    protected void onInactive() {
        stockManager.removeUpdates(listener);
    }

    @Override
    public void setValue(BigDecimal value) {
        super.setValue(value);
    }
}
