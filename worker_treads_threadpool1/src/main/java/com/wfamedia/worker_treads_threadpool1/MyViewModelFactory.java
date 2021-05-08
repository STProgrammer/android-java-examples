package com.wfamedia.worker_treads_threadpool1;

import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.concurrent.Executor;

/**
 * https://developer.android.com/reference/androidx/lifecycle/ViewModelProvider.Factory
 * "Implementations of Factory interface are responsible to instantiate ViewModels."
 */
public class MyViewModelFactory implements ViewModelProvider.Factory {
    private Executor mExecutor;

    public MyViewModelFactory(Executor executor) {
        mExecutor = executor;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new AlbumsViewModel(mExecutor);
    }
}
