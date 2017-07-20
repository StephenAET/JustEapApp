package com.example.stephen.justeatapp;

import android.app.Application;

import com.example.stephen.justeatapp.injection.DaggerPresenterComponent;
import com.example.stephen.justeatapp.injection.PresenterComponent;

public class App extends Application {

    private PresenterComponent presenterComponent;

    public PresenterComponent getPresenterComponent(){
        return presenterComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        presenterComponent = DaggerPresenterComponent.create();
    }
}
