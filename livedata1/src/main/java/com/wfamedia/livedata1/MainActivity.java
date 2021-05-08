package com.wfamedia.livedata1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import android.os.Bundle;
import android.util.Log;
import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Observer<BigDecimal> stockObserver = new Observer<BigDecimal>() {
            @Override
            public void onChanged(BigDecimal bigDecimal) {
                Log.d("STOCK_PRICE", bigDecimal.toString());
            }
        };

        LiveData<BigDecimal> myPriceListener = new StockLiveData("TSL");
        myPriceListener.observe(this, stockObserver);
    }
}