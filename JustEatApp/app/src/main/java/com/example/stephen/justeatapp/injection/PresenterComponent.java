package com.example.stephen.justeatapp.injection;

import com.example.stephen.justeatapp.MapsActivity;

import dagger.Component;

/**
 * Created by Stephen on 20/07/2017.
 */

@Component(dependencies = {PresenterModule.class})
public interface PresenterComponent {
    void inject(MapsActivity target);
}
