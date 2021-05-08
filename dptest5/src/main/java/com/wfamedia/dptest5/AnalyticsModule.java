package com.wfamedia.dptest5;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

/**
 * The Hilt module AnalyticsModule is annotated with @InstallIn(ActivityComponent::class) because
 * you want Hilt to inject that dependency into f.eks. MainActivity. This annotation means that all
 * of the dependencies in AnalyticsModule are available in all of the app's activities.
 */
@Module
@InstallIn(ActivityComponent.class)
public abstract class AnalyticsModule {

    /*
    @Binds
    public abstract AnalyticsService bindAnalyticsService(AnalyticsServiceImpl analyticsServiceImpl);
    */
    @Binds
    public abstract AnalyticsService bindAnalyticsService(AnalyticsServiceImplRandom analyticsServiceImpl);

}
