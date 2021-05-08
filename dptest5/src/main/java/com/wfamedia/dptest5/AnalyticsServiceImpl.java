package com.wfamedia.dptest5;

import javax.inject.Inject;

// Constructor-injected, because Hilt needs to know how to
// provide instances of AnalyticsServiceImpl, too.
public class AnalyticsServiceImpl implements  AnalyticsService {
    @Inject
    AnalyticsServiceImpl() {
    }

    @Override
    public double getCurrentSharePrice(String share) {
        return 11.90;
    }
}
