package com.wfamedia.livedata1;

import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Denne klassen kan f.eks. hente aksjekurser fra Internett for gitt "symbol" i faste intervaller.
 * Dersom endringer i kurs kalles listener.onPriceChanged().
 * Simuleres med en timer som startes når StockLiveData blir AKTIV og stoppes når INAKTIV.
 */
public class StockManager {
    private Timer timer;
    private int seconds = 0;
    private SimplePriceListener simplePriceListener;
    private String symbol;

    public StockManager(String symbol) {
        this.symbol = symbol;
    }

    // Kalles når StockLiveData er AKTIV:
    public void requestPriceUpdates(SimplePriceListener simplePriceListener) {
        this.simplePriceListener = simplePriceListener;
        startTimer();
    }

    // Kalles når StockLiveData blir INAKTIV:
    public void removeUpdates(SimplePriceListener simplePriceListener) {
        this.simplePriceListener = simplePriceListener;
        stopTimer();
    }

    public void startTimer() {
        timer = new Timer();
        try {
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    // guiHandler.post(doUpdateTime);
                    seconds++;
                    simplePriceListener.onPriceChanged(new BigDecimal(seconds * 11412.39));
                }
            }, 0, 3000);

        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        } catch (IllegalStateException ise) {
            //callbackActivity.onTimerCancelled();
        }
    }

    public void stopTimer() {
        if (timer!=null) {
            seconds = 0;
            timer.cancel();
            timer.purge();
        }
    }
}
