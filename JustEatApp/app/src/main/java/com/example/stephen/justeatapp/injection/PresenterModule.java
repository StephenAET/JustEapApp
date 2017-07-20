package com.example.stephen.justeatapp.injection;

import com.example.stephen.justeatapp.mvp.presenter.IRestaurantListPresenter;
import com.example.stephen.justeatapp.mvp.presenter.RestaurantListPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Stephen on 20/07/2017.
 */

@Module
public class PresenterModule {

    @Provides
    public IRestaurantListPresenter provideRestaurantListInteractor(){
        return new RestaurantListPresenterImpl();
    }
}
