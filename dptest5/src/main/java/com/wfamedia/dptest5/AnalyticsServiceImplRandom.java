package com.wfamedia.dptest5;

import javax.inject.Inject;

// Constructor-injected, because Hilt needs to know how to
// provide instances of AnalyticsServiceImpl, too.
public class AnalyticsServiceImplRandom implements  AnalyticsService {
    @Inject
    AnalyticsServiceImplRandom() {
    }

    @Override
    public double getCurrentSharePrice(String share) {
        return Math.random() * 100.0;
    }
}
