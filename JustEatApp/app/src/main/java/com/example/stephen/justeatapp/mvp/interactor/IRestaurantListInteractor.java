package com.example.stephen.justeatapp.mvp.interactor;


import com.example.stephen.justeatapp.model.RestaurantList;

import io.reactivex.Observable;

public interface IRestaurantListInteractor {

    Observable<RestaurantList> getRestaurantList(String location);
}
