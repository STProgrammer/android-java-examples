package com.wfamedia.worker_treads_threadpool1;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Fra dok: public interface ExecutorService implements Executor
public class MyApplication extends Application {

    // ExecutorService: Representerer en pool av tråder. Bruker den til å kjøre Runnables. Systemet velger selv ledig tråd.
    public ExecutorService myExecutorService = Executors.newFixedThreadPool(4);

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
