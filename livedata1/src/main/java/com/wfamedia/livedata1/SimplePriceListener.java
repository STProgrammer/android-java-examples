package com.wfamedia.livedata1;

import java.math.BigDecimal;

public interface SimplePriceListener {
    void onPriceChanged(BigDecimal price);
}
