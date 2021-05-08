package com.wfamedia.dptest5;

import javax.inject.Inject;

/**
 * The @Binds annotation tells Hilt which implementation to use when it needs to
 * provide an instance of an interface.
 *
 */
public class AnalyticsAdapter {

    // Merk: AnalyticsService er et interface!!
    // Faktisk implementasjon i bruk bestemmes av @Bind i AnalyticsModule.
    private final AnalyticsService service;

    // Hvilken AnalyticsService-instans som benyttes er bestemt av @Bind i AnalyticsModule
    @Inject
    AnalyticsAdapter(AnalyticsService service) {
        this.service = service;
    }

    public AnalyticsService getService() {
        return service;
    }
}
